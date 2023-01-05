/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.service;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.CustomHttpTransportClient;
import com.vk.api.sdk.objects.messages.Conversation;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
import ru.thevalidator.daivinchikmatcher.dto.LongPollServerResponse;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Keyboard;
import ru.thevalidator.daivinchikmatcher.handler.Code;
import ru.thevalidator.daivinchikmatcher.handler.Flag;
import ru.thevalidator.daivinchikmatcher.handler.impl.HandlerImpl;
import ru.thevalidator.daivinchikmatcher.parser.ResponseParser;
import ru.thevalidator.daivinchikmatcher.property.Account;
import ru.thevalidator.daivinchikmatcher.property.Delay;
import ru.thevalidator.daivinchikmatcher.property.Proxy;
import ru.thevalidator.daivinchikmatcher.property.UserAgent;
import ru.thevalidator.daivinchikmatcher.handler.Handler;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Task implements Runnable {

    private static final Logger logger = LogManager.getLogger(Task.class);
    //private static final Logger LOG = LoggerFactory.getLogger(Task.class);
    private static final int[] flags = Flag.getAllFlagCodes();
    private static final Random random = new Random();
    private static final int DAI_VINCHIK_BOT_CHAT_ID = -91050183;
    private final Account account;
    private final Proxy proxy;
    private final UserAgent userAgent;
    private final Delay delay;

    public Task(Account account, Proxy proxy, UserAgent userAgent, Delay delay) {
        this.account = account;
        this.proxy = proxy;
        this.userAgent = userAgent;
        this.delay = delay;

        System.out.println("---------------[INFO]---------------\n"
                + "\tname = " + account.getName() + "\n\tuser agent = " + userAgent.getValue()
                + "\n\tproxy status = " + (proxy != null) + "\n\tbase delay = " + delay.getBaseDelay()
                + "\n\trandom delay = " + delay.getRandomAddedDelay()
                + "\n------------------------------------\n");
    }

    @Override
    public void run() {

        TransportClient transportClient = new CustomHttpTransportClient(userAgent.getValue(), proxy);
        VkApiClient vk = new VkApiClient(transportClient);
        UserActor actor = new UserActor(account.getId(), account.getToken());
        Handler handler = new HandlerImpl();

        String answer = null;
        boolean isStartedMessageSent = false;

        try {
            //////

            var conversationsResponse = vk.messages().getConversationsById(actor, DAI_VINCHIK_BOT_CHAT_ID).execute();
            List<Conversation> conversations = conversationsResponse.getItems();
            var messages = conversations.get(0);
            int lastMessageId = messages.getLastMessageId();
            var lastMessage = vk.messages().getById(actor, lastMessageId).execute();
            String lastMessageText = lastMessage.getItems().get(0).getText();

            var kbrd = conversations.get(0).getCurrentKeyboard();
            List<Button> buttons = ResponseParser.parseButtons(kbrd.getButtons().get(0).toString());

            answer = handler.getAnswer(lastMessageText, buttons);

            //////
            var serverData = vk.messages().getLongPollServer(actor).execute();
            String server = serverData.getServer();
            String key = serverData.getKey();
            String ts = String.valueOf(serverData.getTs());

            while (true) {
                ClientResponse response = vk.getTransportClient().get(getLongPollServerRequestAdress(server, key, ts));
                LongPollServerResponse dto = ResponseParser.parseLonPollRespone(response.getContent());
                ts = String.valueOf(dto.getTs());

                //System.out.println("[LPR] " + response.getContent());
                if (!isStartedMessageSent && dto.getUpdates().isEmpty()) {
                    System.out.println("SENDING START MESSAGE");
                    isStartedMessageSent = true;
                    createMessageQuery(vk, actor, answer).executeAsRaw().getContent();
                    //System.out.println(createMessageQuery(vk, actor, answer).executeAsRaw().getContent());
                    //createMessageQuery(vk, actor, answer).execute();
                    continue;
                }

                for (List<Object> o : dto.getUpdates()) {
                    Integer code = (Integer) o.get(0);

                    if (code == Code.INCOMING_MESSAGE) {
                        Integer flagsSum = (Integer) o.get(2);
                        Set<Integer> messageFlags = getFlags(flagsSum);

                        if (!messageFlags.contains(Flag.OUTBOX.getFlagCode())) {
                            Integer minorId = (Integer) o.get(3);
                            if (minorId == DAI_VINCHIK_BOT_CHAT_ID) { //2_000_000_000  // > 
                                //System.out.println("[LPR] " + response.getContent());
                                String message = o.get(5).toString();
                                Keyboard actualKeyboard = ResponseParser.parseKeyboard(o.get(6));
                                //List<Button> responseButtons = actualKeyboard.getButtons().get(0);
                                List<Button> responseButtons = actualKeyboard != null ? actualKeyboard.getButtons().get(0) : null;

                                answer = handler.getAnswer(message, responseButtons);
                                if (answer == null) {
                                    if (message.startsWith("Есть взаимная симпатия! Добавляй в друзья -")) {
                                        System.out.println("[LIKE] " + message);
                                        logger.info("[LIKE] {}", message);
                                        //continue;
                                    } else {
                                        logger.error("\n[LPR]={}\n[ANSWER]=NULL\n", response.getContent().trim());
                                    }
                                    continue;

                                } else if (answer.startsWith("[CASE]-")) {
                                    answer = answer.substring(7);
                                    System.out.println("[LOGGED]");
                                    logger.error("\n[LPR]={}\n[ANSWER]={}\n", response.getContent().trim(), answer);
                                }

                                int timeToWait = delay.getBaseDelay() + random.nextInt(delay.getRandomAddedDelay());
                                System.out.println("SLEEPING " + timeToWait + " secs");
                                TimeUnit.SECONDS.sleep(timeToWait);

                                ClientResponse responseAfterSendMessage = createMessageQuery(vk, actor, answer).executeAsRaw();
                                //logger.info(responseAfterSendMessage.getContent());
                                //System.out.println("[RAW]" + responseAfterSendMessage.getContent());

//                                if () {
//                                    //TODO: CAPTCHA HANDLE
//                                }
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error("[CHECK] - {}", e.getMessage());
            System.out.println("STOPPED, PLEASE PRESS STOP BUTTON AND TRY AGAIN");
        }

        //EventHandler handler = new EventHandler(vk, actor);
        //handler.startHandle();
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    private String getLongPollServerRequestAdress(String server, String key, String ts) {
        return String.format("https://%s?act=a_check&key=%s&ts=%s&wait=25&mode=2&version=3", server, key, ts);
    }

    private MessagesSendQuery createMessageQuery(VkApiClient vk, UserActor actor, String answer) {
        Long timestamp = System.currentTimeMillis();
        MessagesSendQuery query = vk.messages()
                .send(actor)
                .message(answer)
                .peerId(DAI_VINCHIK_BOT_CHAT_ID)
                .randomId(timestamp.intValue());

        return query;
    }

    public Set<Integer> getFlags(int sum) {
        Set<Integer> result = new HashSet<>();
        for (int flag : flags) {
            if ((flag & sum) == flag) {
                result.add(flag);
            }
        }

        return result;
    }

}
