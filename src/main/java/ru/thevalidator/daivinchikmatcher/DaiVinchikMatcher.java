/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.daivinchikmatcher;

import com.formdev.flatlaf.FlatDarkLaf;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import javax.swing.UIManager;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;
import org.fusesource.jansi.AnsiConsole;
import ru.thevalidator.daivinchikmatcher.gui.AppWindow;
import ru.thevalidator.daivinchikmatcher.handler.EventHandler;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class DaiVinchikMatcher {

    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(() -> {
            UIManager.put("Button.arc", 30);
            UIManager.put("ToggleButton.selectedForeground", new java.awt.Color(51, 51, 51));
            FlatDarkLaf.setup();
            
            new AppWindow().setVisible(true);
        });
        
        
////        AnsiConsole.systemInstall();
////        try {
//////            TransportClient transportClient = new HttpTransportClient();
//////            VkApiClient vk = new VkApiClient(transportClient);
//////            UserActor actor = new UserActor(Account.getUserID(), Account.getToken());
//////
//////            EventHandler handler = new EventHandler(vk, actor);
//////            handler.startHandle();
////
////            System.out.println( ansi().eraseScreen().fg(RED).a("Hello").fg(GREEN).a(" World").reset() );
////            System.out.println("FINISH");
////        } catch (Exception e) {
////        } finally {
////            AnsiConsole.systemUninstall();
////        }

    }
}
