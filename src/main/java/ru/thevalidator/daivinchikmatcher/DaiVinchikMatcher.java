/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.daivinchikmatcher;

import com.formdev.flatlaf.FlatDarkLaf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.UIManager;
//import static org.fusesource.jansi.Ansi.Color.GREEN;
//import static org.fusesource.jansi.Ansi.Color.RED;
//import static org.fusesource.jansi.Ansi.ansi;
//import org.fusesource.jansi.AnsiConsole;
import ru.thevalidator.daivinchikmatcher.gui.AppWindow;
import ru.thevalidator.daivinchikmatcher.property.Property;
import ru.thevalidator.daivinchikmatcher.property.Proxy;
import ru.thevalidator.daivinchikmatcher.property.UserAgent;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class DaiVinchikMatcher {

    public static void main(String[] args) {
        
        startGuiApp();

    }

    public static void prepareDefaultPropFile() {
        Property p = new Property();

        ru.thevalidator.daivinchikmatcher.property.Account acc = new ru.thevalidator.daivinchikmatcher.property.Account();
        acc.setName("account 1");
        acc.setToken("token_1");
        ru.thevalidator.daivinchikmatcher.property.Account acc2 = new ru.thevalidator.daivinchikmatcher.property.Account();
        acc2.setName("account 2");
        acc2.setToken("token_2");
        List<ru.thevalidator.daivinchikmatcher.property.Account> accs = new ArrayList<>(Arrays.asList(acc, acc2));

        UserAgent ag = new UserAgent();
        ag.setName("agent name 1");
        ag.setValue("agent value 1");
        UserAgent ag2 = new UserAgent();
        ag2.setName("agent name 2");
        ag2.setValue("agent value 2");
        List<UserAgent> agents = new ArrayList<>(Arrays.asList(ag, ag2));

        Proxy pr = new Proxy();
        pr.setAdress("xxx.xxx.xxx.xxx");
        pr.setPort(1111);
        Proxy pr2 = new Proxy();
        pr2.setAdress("yyy.yyy.yyy.yyy");
        pr2.setPort(2222);
        List<Proxy> proxies = new ArrayList<>(Arrays.asList(pr, pr2));

        p.setAccounts(accs);
        p.setUserAgents(agents);
        p.setProxies(proxies);

        Property.saveToJson(p);
    }

    public static void startGuiApp() {
        java.awt.EventQueue.invokeLater(() -> {
            UIManager.put("Button.arc", 30);
            UIManager.put("ToggleButton.selectedForeground", new java.awt.Color(51, 51, 51));
            FlatDarkLaf.setup();

            new AppWindow().setVisible(true);
        });
    }
}
