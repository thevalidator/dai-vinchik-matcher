/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.exception;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class TooManyLikesException extends RuntimeException {

    public TooManyLikesException() {
        super("Слишком много лайков за сегодня. Загляни попозже");
    }

}
