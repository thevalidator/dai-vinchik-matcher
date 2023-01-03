/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.property;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class UserAgent {
    
    private String name;
    private String value;

    public UserAgent() {
    }

    public UserAgent(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
