/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.property;

import java.util.List;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Property {
    
    public static final String PROP_FILE = "properties.json";
    private List<Account> accounts;
    private List<UserAgent> userAgents;
    private List<Proxy> proxies;
    private Delay delay;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<UserAgent> getUserAgents() {
        return userAgents;
    }

    public void setUserAgents(List<UserAgent> userAgents) {
        this.userAgents = userAgents;
    }

    public List<Proxy> getProxies() {
        return proxies;
    }

    public void setProxies(List<Proxy> proxies) {
        this.proxies = proxies;
    }    

    public Delay getDelay() {
        return delay;
    }

    public void setDelay(Delay delay) {
        this.delay = delay;
    }

}
