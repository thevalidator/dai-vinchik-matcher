/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class TimestampUtil {
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static String convertTimestampToData(long timestamp) {
        Date currentDate = new Date(timestamp * 1_000);
        return dateFormat.format(currentDate);
    }

    public static long convertDateToTimestamp(String date) throws ParseException {
        return dateFormat.parse(date).getTime() / 1_000;
    }
    
    public static long getTimestampOfNow() {
        return System.currentTimeMillis() / 1_000;
    }

}
