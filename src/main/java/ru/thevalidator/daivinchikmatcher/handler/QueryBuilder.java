/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import java.util.LinkedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static ru.thevalidator.daivinchikmatcher.property.Data.DAI_VINCHIK_BOT_CHAT_ID;
import ru.thevalidator.daivinchikmatcher.util.FileUtil;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class QueryBuilder {

    //private static final String MESSAGE = FileUtil.readMessageDict("config/message.txt");
    private static final Logger logger = LogManager.getLogger(QueryBuilder.class);
    public static final LinkedList<String> MESSAGES = FileUtil.readMessagesDict("config/messages.txt");
    private final VkApiClient vk;
    private final UserActor actor;
    private final boolean IS_DEBUG_MODE;

    public QueryBuilder(VkApiClient vk, UserActor actor, boolean isDebugMode) {
        this.vk = vk;
        this.actor = actor;
        this.IS_DEBUG_MODE = isDebugMode;
    }

    public VkApiClient getVk() {
        return vk;
    }

    public UserActor getActor() {
        return actor;
    }

    public MessagesSendQuery buildDVAnswer(String answer) {
        if (IS_DEBUG_MODE) {
            logger.info(" [ANSWER] " + answer);
        }
        Long timestamp = System.currentTimeMillis();
        MessagesSendQuery query = vk.messages()
                .send(actor)
                .message(answer)
                .peerId(DAI_VINCHIK_BOT_CHAT_ID)
                .randomId(timestamp.intValue());

        return query;
    }

    public MessagesSendQuery buildReplyForLikedUser(int targetId) {
        Long timestamp = System.currentTimeMillis();
        String message;
        synchronized (MESSAGES) {
            message = MESSAGES.pollFirst();
            MESSAGES.addLast(message);
        }
        MessagesSendQuery query = vk.messages()
                .send(actor)
                .message(message)
                .peerId(targetId)
                .randomId(timestamp.intValue());

        return query;
    }

}
