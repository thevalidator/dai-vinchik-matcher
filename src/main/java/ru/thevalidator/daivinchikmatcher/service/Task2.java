/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.service;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.CustomUserActor;
import com.vk.api.sdk.httpclient.CustomHttpTransportClient;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.daivinchikmatcher.gui.AppWindow;
import ru.thevalidator.daivinchikmatcher.notification.Informer;
import ru.thevalidator.daivinchikmatcher.property.Account;
import ru.thevalidator.daivinchikmatcher.property.Proxy;
import ru.thevalidator.daivinchikmatcher.property.UserAgent;
import ru.thevalidator.daivinchikmatcher.settings.Parameter;
import ru.thevalidator.daivinchikmatcher.util.ExceptionUtil;
import ru.thevalidator.daivinchikmatcher.v2.identity.impl.Handlermpl;
import ru.thevalidator.daivinchikmatcher.v2.identity.Handler;
import ru.thevalidator.daivinchikmatcher.matcher.ProfileMatcher;
import ru.thevalidator.daivinchikmatcher.util.DBUtil;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Task2 extends Informer implements Runnable {

    private static final Logger logger = LogManager.getLogger(Task.class);
    private static final Random random = new Random();
    private static final int BASE_DELAY = (int) AppWindow.getSettings().get(Parameter.BASE_DELAY);
    private static final int RANDOM_DELAY = (int) AppWindow.getSettings().get(Parameter.RANDOM_DELAY);

    private final Account account;
    private final Proxy proxy;
    private final UserAgent userAgent;
    private final ProfileMatcher matchChecker;

    public Task2(Account account, Proxy proxy, UserAgent userAgent, ProfileMatcher matchChecker) {
        this.account = account;
        this.proxy = proxy;
        this.userAgent = userAgent;
        this.matchChecker = matchChecker;
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

        TransportClient transportClient = new CustomHttpTransportClient(userAgent.getValue(), proxy);
        VkApiClient vk = new VkApiClient(transportClient);
        CustomUserActor actor = new CustomUserActor(account.getToken());

        try {

            var acc = vk.account().getProfileInfo(actor).execute();
            Integer id = vk.users().get(actor).execute().get(0).getId();
            
            actor.setUserName(acc.getFirstName(), acc.getLastName());
            actor.setUserId(id);
            
            if (!DBUtil.isUserExists(id)) {
                DBUtil.insertUser(acc.getFirstName(), acc.getLastName(), id);
                informObservers(actor.getUserName() + "\n> NEW ACCOUNT WAS SAVED INTO DB");
            }
            
            // TODO: check if chat with DV exists and have profile filled
            
            Handler handler = new Handlermpl(vk, actor, matchChecker);
            ((Handlermpl) handler).setInformer(this);
            
            int counter = (int) AppWindow.getSettings().get(Parameter.REPLY_CHECK_PERIOD);
            while (true) {
                if (counter < Integer.MAX_VALUE) {
                    counter++;
                } else {
                    counter = 1;
                }
                
                handler.handleLastMessage();

                int timeToWait = BASE_DELAY + random.nextInt(RANDOM_DELAY);
                informObservers(actor.getUserName() + "\n> SLEEPING " + timeToWait + " secs");
                TimeUnit.SECONDS.sleep(timeToWait);
                
                counter -= timeToWait;
                if (counter <= 0) {
                    informObservers(actor.getUserName() + "\n> [CHECKING UNHANDLED LIKES IN DB]");
                    counter = (int) AppWindow.getSettings().get(Parameter.REPLY_CHECK_PERIOD);
                    List<Integer> likes = DBUtil.getLikedUsersVkId(actor.getId());
                    if (!likes.isEmpty()) {
                        informObservers(actor.getUserName() + "\n> [LIKES FOUND] " + likes.size() + " pcs.");
                        handler.handleReplies(likes);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(ExceptionUtil.getFormattedDescription(e));
        }
        
    }

}
