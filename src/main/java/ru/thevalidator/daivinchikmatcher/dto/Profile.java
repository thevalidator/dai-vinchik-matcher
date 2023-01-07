/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.dto;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Profile {
    
    private final String name;
    private final String age;
    private final String city;
    private final String text;

    public Profile(String name, String age, String city, String text) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getText() {
        return text;
    }

}
