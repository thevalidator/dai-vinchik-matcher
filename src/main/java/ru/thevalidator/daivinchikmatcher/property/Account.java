/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.property;

//import java.util.HashSet;
//import java.util.Set;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Account {
    
    private String name;
    private String token;
//    private Set<String> likedProfiles;

    public Account() {
//        likedProfiles = new HashSet<>();
    }

    public Account(String name, String token) {
//        this();
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
//    public void addProfileToLiked(String id) {
//        likedProfiles.add(id);
//    }
//    
//    public boolean likedHaveProfile(String id) {
//        return likedProfiles.contains(id);
//    }
//    
//    public void deleteProfileFromLiked(String id) {
//        likedProfiles.remove(id);
//    }

}
