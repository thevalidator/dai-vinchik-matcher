/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.dto.settings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class SettingsDTO {
    
//    WINDOW_DIMENTIONS,
//    LONG_POLL_DELAY,
//    AGE_FILTER,
//    CITY_FILTER,
//    TEXT_FILTER,
//    SOUND_ALARM,
//    LIKE_ON_LIKE,
//    EXPERIMENTAL_HANDLER,
//    HOURS_TO_SLEEP,
//    DEBUG_MODE,
//    BASE_DELAY,
//    RANDOM_DELAY
//    REPLY_CHECK_PERIOD
    
    @JsonProperty("window dimensions")
    private int[] windowDimentions;
    @JsonProperty("long poll delay")
    private String longPollDelay;
    
    @JsonProperty("base delay")
    private int baseDelay;
    @JsonProperty("random delay")
    private int randomDelay;
    
    @JsonProperty("age filter")
    private boolean hasAgeFilter;
    @JsonProperty("city filter")
    private boolean hasCityFilter;
    @JsonProperty("text filter")
    private boolean hasTextFilter;
    
    @JsonProperty("sound")
    private boolean hasSoundAlarm;
    @JsonProperty("like on like")
    private boolean shouldLikeOnLike;
    @JsonProperty("experimental functions")
    private boolean hasExperimentalHandler;
    
    @JsonProperty("sleep hours")
    private int hoursToSleep;
    @JsonProperty("debug mode")
    private boolean isDebugMode;
    @JsonProperty("reply check period")
    private int replyCheckPeriod;

    public int[] getWindowDimentions() {
        return windowDimentions;
    }

    public void setWindowDimentions(int[] windowDimentions) {
        this.windowDimentions = windowDimentions;
    }

    public String getLongPollDelay() {
        return longPollDelay;
    }

    public void setLongPollDelay(String longPollDelay) {
        this.longPollDelay = longPollDelay;
    }

    public boolean hasAgeFilter() {
        return hasAgeFilter;
    }

    public void setAgeFilter(boolean ageFilter) {
        this.hasAgeFilter = ageFilter;
    }

    public boolean hasCityFilter() {
        return hasCityFilter;
    }

    public void setCityFilter(boolean cityFilter) {
        this.hasCityFilter = cityFilter;
    }

    public boolean hasTextFilter() {
        return hasTextFilter;
    }

    public void setTextFilter(boolean textFilter) {
        this.hasTextFilter = textFilter;
    }

    public boolean hasSoundAlarm() {
        return hasSoundAlarm;
    }

    public void setSoundAlarm(boolean soundAlarm) {
        this.hasSoundAlarm = soundAlarm;
    }

    public boolean shouldLikeOnLike() {
        return shouldLikeOnLike;
    }

    public void setLikeOnLike(boolean likeOnLike) {
        this.shouldLikeOnLike = likeOnLike;
    }

    public boolean hasExperimentalHandler() {
        return hasExperimentalHandler;
    }

    public void setExperimentalHandler(boolean experimentalHandler) {
        this.hasExperimentalHandler = experimentalHandler;
    }

    public int getHoursToSleep() {
        return hoursToSleep;
    }

    public void setHoursToSleep(int hoursToSleep) {
        this.hoursToSleep = hoursToSleep;
    }

    public boolean isDebugMode() {
        return isDebugMode;
    }

    public int getReplyCheckPeriod() {
        return replyCheckPeriod;
    }

    public void setReplyCheckPeriod(int replyCheckPeriod) {
        this.replyCheckPeriod = replyCheckPeriod;
    }

    @JsonIgnore
    public void setDebugMode(boolean debugMode) {
        this.isDebugMode = debugMode;
    }

    public int getBaseDelay() {
        return baseDelay;
    }

    public void setBaseDelay(int baseDelay) {
        this.baseDelay = baseDelay;
    }

    public int getRandomDelay() {
        return randomDelay;
    }

    public void setRandomDelay(int randomDelay) {
        this.randomDelay = randomDelay;
    }
    
}
