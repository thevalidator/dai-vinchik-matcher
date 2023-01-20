/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.CustomHttpTransportClient;
import com.vk.api.sdk.objects.messages.Conversation;
import com.vk.api.sdk.objects.messages.GetHistoryRev;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static ru.thevalidator.daivinchikmatcher.property.Data.DAI_VINCHIK_BOT_CHAT_ID;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class VKUtilTest {
    
    
    private static int peerId = -91050183;
    private static VkApiClient vk;
    private static UserActor actor;
    
    public VKUtilTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        String token = "vk1.a.V9yBC20P7DNyRypOrsqbc9kApqKQSNOwtvTv4hfDDpGC6fPsragEWC16KMDCjjDwsbPanBo2H1EvSGCGGKYAtwrSocDQSswMsusiOg1GgnCGxDSt743CCTQ_nCEJ9SPhXoxeZ1_73gJcZB6GB5aW4dTEZfIVMu1ohaBh-SHXToySDsNO_gxmpoDe9sBbg8FYa1r6WO40Le7FNTicqexIAQ";
        TransportClient transportClient = new CustomHttpTransportClient(null, null);
        vk = new VkApiClient(transportClient);
        actor = new UserActor(519324877, token);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetConversation() throws Exception {
        
        var conversationsResponse = vk.messages().getConversationsById(actor, peerId).execute();
        List<Conversation> conversations = conversationsResponse.getItems();
        System.out.println("size: " + conversations.size());
        ObjectMapper mapper = new ObjectMapper();
        String output = mapper.writeValueAsString(conversations.get(0));
        System.out.println(output);
        
        String history = vk.messages()
                .getHistory(actor)
                .peerId(DAI_VINCHIK_BOT_CHAT_ID)
                .count(5)
                .rev(GetHistoryRev.REVERSE_CHRONOLOGICAL)
                .executeAsString();
        
        System.out.println("history: " + history);
        
        System.out.println("last msg: " + vk.messages().getById(actor, 1630455).executeAsString());
        
        var keyboard = conversations.get(0).getCurrentKeyboard();
        System.out.println("key: " + keyboard.toPrettyString());
        
        
        
        //Conversation expResult = null;
//        Conversation result = VKUtil.getConversation(vk, actor, peerId);
//        result.getMessageRequestData()
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
    }
    
}
