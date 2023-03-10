/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.service;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.client.actors.UserActorWithoutId;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.CustomHttpTransportClient;
import com.vk.api.sdk.objects.account.responses.GetProfileInfoResponse;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.daivinchikmatcher.dto.LongPollServerResponse;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;
import ru.thevalidator.daivinchikmatcher.exception.TooManyLikesException;
import ru.thevalidator.daivinchikmatcher.gui.AppWindow;
import ru.thevalidator.daivinchikmatcher.handler.impl.HandlerImpl;
import ru.thevalidator.daivinchikmatcher.parser.ResponseParser;
import ru.thevalidator.daivinchikmatcher.property.Account;
import ru.thevalidator.daivinchikmatcher.property.Proxy;
import ru.thevalidator.daivinchikmatcher.property.UserAgent;
import ru.thevalidator.daivinchikmatcher.handler.Handler;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import ru.thevalidator.daivinchikmatcher.notification.Informer;
import static ru.thevalidator.daivinchikmatcher.property.Data.DAI_VINCHIK_BOT_CHAT_ID;
import ru.thevalidator.daivinchikmatcher.settings.Parameter;
import ru.thevalidator.daivinchikmatcher.util.ExceptionUtil;
import ru.thevalidator.daivinchikmatcher.util.VKUtil;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Task extends Informer implements Runnable {

    private static final Logger logger = LogManager.getLogger(Task.class);
    private static final Random random = new Random();
    private static final String RESPONSE_DELAY = (String) AppWindow.getSettings().get(Parameter.LONG_POLL_DELAY);
    private static final int BASE_DELAY = (int) AppWindow.getSettings().get(Parameter.BASE_DELAY);
    private static final int RANDOM_DELAY = (int) AppWindow.getSettings().get(Parameter.RANDOM_DELAY);
    private static final boolean IS_DEBUG_MODE = (boolean) AppWindow.getSettings().get(Parameter.DEBUG_MODE);

    private final Account account;
    private final Proxy proxy;
    private final UserAgent userAgent;
    private Set<Filter> filters;

    public Task(Account account, Proxy proxy, UserAgent userAgent, Set<Filter> filters) {
        this.account = account;
        this.proxy = proxy;
        this.userAgent = userAgent;
        this.filters = filters;
    }

    @Override
    public void run() {

        String threadName = Thread.currentThread().getName();
        String info = "[INFO]"
                + "\n> thread: " + threadName
                + "\n> name = " + account.getName()
                + "\n> user agent = " + userAgent.getValue()
                + "\n> proxy status = " + (proxy != null);

        informObservers(info);

        class QueryBuilder {

            private final VkApiClient vk;
            private final UserActor actor;

            public QueryBuilder(VkApiClient vk, UserActor actor) {
                this.vk = vk;
                this.actor = actor;
            }

            public MessagesSendQuery build(String answer) {
                Long timestamp = System.currentTimeMillis();
                MessagesSendQuery query = vk.messages()
                        .send(actor)
                        .message(answer)
                        .peerId(DAI_VINCHIK_BOT_CHAT_ID)
                        .randomId(timestamp.intValue());

                return query;
            }
        }

        TransportClient transportClient = new CustomHttpTransportClient(userAgent.getValue(), proxy);
        VkApiClient vk = new VkApiClient(transportClient);
        UserActorWithoutId actor = new UserActorWithoutId(account.getToken());

        QueryBuilder query = new QueryBuilder(vk, actor);
        Handler handler = new HandlerImpl(filters, vk, actor);
        ((HandlerImpl) handler).setInformer(this);

        String answer = null;
        ClientResponse response = null;

        try {
            //get profile info
            GetProfileInfoResponse profileInfo = vk.account().getProfileInfo(actor).execute();
            String accName = "??????????????: " + profileInfo.getFirstName() + " " + profileInfo.getLastName();
            actor.setUserName(accName);
            //informObservers(accName);

            //get last incoming message and actual keyboard     
            var messages = VKUtil.getConversation(vk, actor, DAI_VINCHIK_BOT_CHAT_ID);
            int lastMessageId = messages.getLastMessageId();
            var lastMessage = vk.messages().getById(actor, lastMessageId).execute();
            var kbrd = messages.getCurrentKeyboard();

            String lastMessageText = lastMessage.getItems().get(0).getText();
            List<Button> buttons = ResponseParser.parseButtons(kbrd.getButtons().get(kbrd.getButtons().size() - 1).toString());

            //TODO: probably need to check msg before for mutual like if sleepeing case found
            answer = handler.getStartMessage(lastMessageText, buttons);
            if (IS_DEBUG_MODE) {
                logger.info(" START " + lastMessage + "\nKBRD " + kbrd.toPrettyString() + "\nANSWER " + answer);
            }
            
            // get long poll server data for connection
            var serverData = vk.messages().getLongPollServer(actor).execute();
            String server = serverData.getServer();
            String key = serverData.getKey();
            String ts = String.valueOf(serverData.getTs());
            
            sendAnswer(query.build(answer));

            while (true) {
                int timeToWait = BASE_DELAY + random.nextInt(RANDOM_DELAY);
                informObservers(actor.getUserName() + "\n> SLEEPING " + timeToWait + " secs");
                TimeUnit.SECONDS.sleep(timeToWait);
                
                response = vk.getTransportClient().get(getLongPollServerRequestAdress(server, key, ts));
                String responseContent = response.getContent().trim();
                if (IS_DEBUG_MODE) {
                    logger.info(responseContent);
                }
                if (responseContent.startsWith("{\"failed\":")) {
                    char errorCode = responseContent.charAt(10);
                    //"failed":1 ??? ?????????????? ?????????????? ???????????????? ?????? ???????? ???????????????? ??????????????, ???????????????????? ?????????? ???????????????? ?????????????? ??????????, ?????????????????? ?????????? ???????????????? ts ???? ????????????.
                    //"failed":2 ??? ?????????????? ?????????? ???????????????? ??????????, ?????????? ???????????? ???????????????? key ?????????????? messages.getLongPollServer.
                    //"failed":3 ??? ???????????????????? ?? ???????????????????????? ????????????????, ?????????? ?????????????????? ?????????? key ?? ts ?????????????? messages.getLongPollServer.
                    switch (errorCode) {
                        case '1' ->
                            ts = responseContent.substring(17).replaceAll("[^0-9]", "");
                        case '2' -> {
                            serverData = vk.messages().getLongPollServer(actor).execute();
                            key = serverData.getKey();
                        }
                        default -> {
                            serverData = vk.messages().getLongPollServer(actor).execute();
                            key = serverData.getKey();
                            ts = String.valueOf(serverData.getTs());
                        }
                    }

                    //get last incoming message and actual keyboard     
                    messages = VKUtil.getConversation(vk, actor, DAI_VINCHIK_BOT_CHAT_ID);
                    lastMessageId = messages.getLastMessageId();
                    lastMessage = vk.messages().getById(actor, lastMessageId).execute();
                    kbrd = messages.getCurrentKeyboard();

                    lastMessageText = lastMessage.getItems().get(0).getText();
                    buttons = ResponseParser.parseButtons(kbrd.getButtons().get(kbrd.getButtons().size() - 1).toString());

                    //TODO: probably need to check msg before for mutual like if sleepeing case found
                    answer = handler.getStartMessage(lastMessageText, buttons);
                    if (IS_DEBUG_MODE) {
                        logger.info(" [MISSED] " + lastMessage + "\nKBRD " + kbrd.toPrettyString() + "\nANSWER " + answer);
                    }
                    sendAnswer(query.build(answer));

                    continue;
                }

                LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
                ts = String.valueOf(dto.getTs());
                answer = handler.getAnswerMessage(dto.getUpdates());
                if (answer == null) {
                    informObservers("> [PIZDA TUT] - ?????????????????? ????????????????????, ???????? ?? ?????????????? ???? ?????????????????? ???????????????????? ?? ?????????????? ??????????");
                    //get last incoming message and actual keyboard     
                    messages = VKUtil.getConversation(vk, actor, DAI_VINCHIK_BOT_CHAT_ID);
                    lastMessageId = messages.getLastMessageId();
                    lastMessage = vk.messages().getById(actor, lastMessageId).execute();
                    kbrd = messages.getCurrentKeyboard();

                    lastMessageText = lastMessage.getItems().get(0).getText();
                    buttons = ResponseParser.parseButtons(kbrd.getButtons().get(kbrd.getButtons().size() - 1).toString());

                    //TODO: probably need to check msg before for mutual like if sleepeing case found
                    answer = handler.getStartMessage(lastMessageText, buttons);
                    if (IS_DEBUG_MODE) {
                        logger.info(" [????????????????] " + lastMessage + "\nKBRD " + kbrd.toPrettyString() + "\nANSWER " + answer);
                    }
                    
                }
                sendAnswer(query.build(answer));
            }

        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                //System.out.println("====== STOPPED ======");
                //informObservers(threadName + "\n====== STOPPED ======");
            } else if (e instanceof TooManyLikesException) {

                informObservers(actor.getUserName() + "\n> REACHED MAX LIKES PER DAY");

            } else {
                informObservers(actor.getUserName() + "\n> [ERROR] " + e.getMessage());
                if (response != null) {
                    logger.error("[LPR] - {}", response.getContent());
                }
                logger.error("[CHECK] - {}", ExceptionUtil.getFormattedDescription(e));
            }
            informObservers(actor.getUserName() + "\n====== STOPPED ======");
        }

    }

    private void sendAnswer(MessagesSendQuery query) throws ClientException {
        ClientResponse response = query.executeAsRaw();
        if (IS_DEBUG_MODE) {
            logger.info(" SEND_RESPONSE " + response.getContent());
        }
//                                if () {
//                                    //TODO: CAPTCHA HANDLE
//                                }
    }

    private String getLongPollServerRequestAdress(String server, String key, String ts) {
        return String.format("https://%s?act=a_check&key=%s&ts=%s&wait=%s&mode=2&version=3", server, key, ts, RESPONSE_DELAY);
    }

}
