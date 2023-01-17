/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.util;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class EmojiCleaner {
    
    private static final int FIRST_EMOJI_CODE = 8986;
    private static final int LAST_EMOJI_CODE = 129510;
    
    public static String clean(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c < FIRST_EMOJI_CODE || c > LAST_EMOJI_CODE) {
                sb.append(c);
            }
        }
        
        return sb.toString();
    }

}
