/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.v2.identity;

import java.util.List;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Handler {

    void handleLastMessage();

    void handleReplies(List<Integer> likes);

}
