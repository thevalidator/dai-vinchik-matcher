/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import static ru.thevalidator.daivinchikmatcher.property.Data.DAI_VINCHIK_BOT_CHAT_ID;
import ru.thevalidator.daivinchikmatcher.util.FileUtil;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class QueryBuilder {

    private static final String MESSAGE = FileUtil.readMessageDict("config/message.txt");
    private final VkApiClient vk;
    private final UserActor actor;

    public QueryBuilder(VkApiClient vk, UserActor actor) {
        this.vk = vk;
        this.actor = actor;
    }

    public VkApiClient getVk() {
        return vk;
    }

    public UserActor getActor() {
        return actor;
    }

    public MessagesSendQuery buildDVAnswer(String answer) {
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
        String message = MESSAGE;
        MessagesSendQuery query = vk.messages()
                .send(actor)
                .message(message)
                .peerId(targetId)
                .randomId(timestamp.intValue());

        return query;
    }

}
