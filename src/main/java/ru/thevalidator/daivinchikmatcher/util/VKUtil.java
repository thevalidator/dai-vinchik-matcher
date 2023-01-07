/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.util;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Conversation;
import java.util.List;
import static ru.thevalidator.daivinchikmatcher.property.Data.DAI_VINCHIK_BOT_CHAT_ID;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class VKUtil {

    public static Conversation getConversation(VkApiClient vk, UserActor actor, int conversationId) throws ApiException, ClientException {
        var conversationsResponse = vk.messages().getConversationsById(actor, DAI_VINCHIK_BOT_CHAT_ID).execute();
        List<Conversation> conversations = conversationsResponse.getItems();
        
        return conversations.get(0);
    }

}
