/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.exception;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class CannotLoadSettingException extends RuntimeException {

    public CannotLoadSettingException() {
        super("Load settings error");
    }
    
}
