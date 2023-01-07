/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.util;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class ExceptionUtil {
    
    public static String getFormattedDescription(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getMessage()).append("\n");
        for (StackTraceElement el : e.getStackTrace()) {
            sb.append(el.toString()).append("\n");
        }
        
        return sb.toString().trim();
    } 

}
