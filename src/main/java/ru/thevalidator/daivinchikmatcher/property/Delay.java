/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.property;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Delay {

    private int baseDelay;
    private int randomAddedDelay;

    public int getBaseDelay() {
        return baseDelay;
    }

    public void setBaseDelay(int baseDelay) {
        this.baseDelay = baseDelay;
    }

    public int getRandomAddedDelay() {
        return randomAddedDelay;
    }

    public void setRandomAddedDelay(int randomAddedDelay) {
        this.randomAddedDelay = randomAddedDelay;
    }
    
}
