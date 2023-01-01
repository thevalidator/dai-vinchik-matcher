/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.nio.file.Paths;
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

    public static void saveToJson(Property p) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(Paths.get(PROP_FILE).toFile(), p);
        } catch (IOException e) {
        }
    }
    
    public static Property readFromJson(String path) {
        Property p = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            p = mapper.readValue(Paths.get(path).toFile(), Property.class);
        } catch (IOException e) {
        }
        return p;
    }
    
    @JsonIgnore
    public String[] getAccountNames() {
        String[] items = new String[accounts.size()];
        for (int i = 0; i < accounts.size(); i++) {
            items[i] = accounts.get(i).getName();
        }
        
        return items;
    }
    
    @JsonIgnore
    public String[] getProxyAdresses() {
        String[] items = new String[proxies.size()];
        for (int i = 0; i < proxies.size(); i++) {
            items[i] = (i + 1) + " - " + "[" + proxies.get(i).getAdress() + " : " + proxies.get(i).getPort() + "]";
        }
        
        return items;
    }
    
    @JsonIgnore
    public String[] getUserAgentsNames() {
        String[] items = new String[userAgents.size()];
        for (int i = 0; i < userAgents.size(); i++) {
            items[i] = userAgents.get(i).getName();
        }
        
        return items;
    }
    

}
