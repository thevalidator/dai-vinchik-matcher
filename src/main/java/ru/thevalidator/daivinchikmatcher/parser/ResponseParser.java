/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.thevalidator.daivinchikmatcher.dto.LongPollServerResponse;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Keyboard;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.KeyboardRoot;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class ResponseParser {
    
    private static ObjectMapper mapper = new ObjectMapper();
    
    public static Keyboard parseKeyboard(Object data) {
        KeyboardRoot root = mapper.convertValue(data, KeyboardRoot.class);
        return root.getKeyboard();
    }
    
    public static LongPollServerResponse parseLonPollRespone(String response) throws JsonProcessingException {
        return mapper.readValue(response, LongPollServerResponse.class);
    }

}
