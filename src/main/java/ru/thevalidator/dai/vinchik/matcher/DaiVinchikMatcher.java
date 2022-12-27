/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.dai.vinchik.matcher;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.account.responses.GetProfileInfoResponse;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class DaiVinchikMatcher {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient); 
        UserActor actor = new UserActor(Account.getUserID(), Account.getToken());
        
        try {
            GetProfileInfoResponse profileInfo = vk.account().getProfileInfo(actor).execute();
            System.out.println(profileInfo.getFirstName());
        } catch (Exception e) {
        }
        
    }
}
