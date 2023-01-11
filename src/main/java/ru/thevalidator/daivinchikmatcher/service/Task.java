/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.service;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.CustomHttpTransportClient;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.daivinchikmatcher.dto.LongPollServerResponse;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;
import ru.thevalidator.daivinchikmatcher.handler.impl.HandlerImpl;
import ru.thevalidator.daivinchikmatcher.parser.ResponseParser;
import ru.thevalidator.daivinchikmatcher.property.Account;
import ru.thevalidator.daivinchikmatcher.property.Delay;
import ru.thevalidator.daivinchikmatcher.property.Proxy;
import ru.thevalidator.daivinchikmatcher.property.UserAgent;
import ru.thevalidator.daivinchikmatcher.handler.Handler;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import static ru.thevalidator.daivinchikmatcher.property.Data.DAI_VINCHIK_BOT_CHAT_ID;
import ru.thevalidator.daivinchikmatcher.util.ExceptionUtil;
import ru.thevalidator.daivinchikmatcher.util.FileUtil;
import ru.thevalidator.daivinchikmatcher.util.VKUtil;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Task implements Runnable {

    private static final Logger logger = LogManager.getLogger(Task.class);
    private static final String responseDelay = FileUtil.readDelay("delay.txt");

    private static final Random random = new Random();
    private final Account account;
    private final Proxy proxy;
    private final UserAgent userAgent;
    private final Delay delay;
    private Set<Filter> filters;

    public Task(Account account, Proxy proxy, UserAgent userAgent, Delay delay, Set<Filter> filters) {
        this.account = account;
        this.proxy = proxy;
        this.userAgent = userAgent;
        this.delay = delay;
        this.filters = filters;

        System.out.println("---------------[INFO]---------------\n"
                + "\tname = " + account.getName() + "\n\tuser agent = " + userAgent.getValue()
                + "\n\tproxy status = " + (proxy != null) + "\n\tbase delay = " + delay.getBaseDelay()
                + "\n\trandom delay = " + delay.getRandomAddedDelay()
                + "\n------------------------------------\n");
    }

    @Override
    public void run() {

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
        UserActor actor = new UserActor(account.getId(), account.getToken());

        QueryBuilder query = new QueryBuilder(vk, actor);
        Handler handler = new HandlerImpl(filters, vk, actor);

        String answer = null;
        ClientResponse response = null;

        try {
            //get last incoming message and actual keyboard     
            var messages = VKUtil.getConversation(vk, actor, DAI_VINCHIK_BOT_CHAT_ID);
            int lastMessageId = messages.getLastMessageId();
            var lastMessage = vk.messages().getById(actor, lastMessageId).execute();
            var kbrd = messages.getCurrentKeyboard();

            String lastMessageText = lastMessage.getItems().get(0).getText();
            List<Button> buttons = ResponseParser.parseButtons(kbrd.getButtons().get(kbrd.getButtons().size() - 1).toString());

            answer = handler.getStartMessage(lastMessageText, buttons);
            System.out.println("[ANSWER] - " + answer);
            sendAnswer(query.build(answer));
            
            // get long poll server data for connection
            var serverData = vk.messages().getLongPollServer(actor).execute();
            String server = serverData.getServer();
            String key = serverData.getKey();
            String ts = String.valueOf(serverData.getTs());

            while (true) {
                int timeToWait = delay.getBaseDelay() + random.nextInt(delay.getRandomAddedDelay());
                System.out.println("SLEEPING " + timeToWait + " secs");
                TimeUnit.SECONDS.sleep(timeToWait);
                response = vk.getTransportClient().get(getLongPollServerRequestAdress(server, key, ts));
                String responseContent = response.getContent();
                System.out.println("[LPR] " + responseContent.trim());
                if (responseContent.startsWith("{\"failed\":")) {
                    char errorCode = responseContent.charAt(10);
                    //"failed":1 — история событий устарела или была частично утеряна, приложение может получать события далее, используя новое значение ts из ответа.
                    //"failed":2 — истекло время действия ключа, нужно заново получить key методом messages.getLongPollServer.
                    //"failed":3 — информация о пользователе утрачена, нужно запросить новые key и ts методом messages.getLongPollServer.
                    switch (errorCode) {
                        case '1' ->
                            ts = responseContent.substring(17, responseContent.length() - 1);
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
                    continue;
                }

                LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
                ts = String.valueOf(dto.getTs());
                answer = handler.getAnswerMessage(dto.getUpdates());
                if (answer == null) {
                    continue;
                }
                System.out.println("[ANSWER] - " + answer);
                sendAnswer(query.build(answer));
                //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>  **********  <<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
            }

        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                System.out.println("====== STOPPED ======");
            } else {
                System.out.println(e.getMessage());
                if (response != null) {
                    logger.error("[LPR] - {}", response.getContent());
                }
                logger.error("[CHECK] - {}", ExceptionUtil.getFormattedDescription(e));
                System.out.println("====== STOPPED, PLEASE PRESS STOP BUTTON AND TRY AGAIN ======");
            }
        }

    }

    private void sendAnswer(MessagesSendQuery query) throws ClientException {
        ClientResponse response = query.executeAsRaw();
        //logger.info(response.getContent());
        System.out.println("[RAW] - " + response.getContent() + "\n\n");

//                                if () {
//                                    //TODO: CAPTCHA HANDLE
//                                }
    }

    private String getLongPollServerRequestAdress(String server, String key, String ts) {
        return String.format("https://%s?act=a_check&key=%s&ts=%s&wait=%s&mode=2&version=3", server, key, ts, responseDelay);
    }

}
