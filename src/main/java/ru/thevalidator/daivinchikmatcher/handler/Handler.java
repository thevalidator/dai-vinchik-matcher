/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.handler;

import java.util.List;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Handler {
    
    String getAnswer(String messageText, List<Button> buttons);

}
