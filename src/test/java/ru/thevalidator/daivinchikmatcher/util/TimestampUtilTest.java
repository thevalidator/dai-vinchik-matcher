/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.util;

import java.text.ParseException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class TimestampUtilTest {
    
    static long timestamp = 1674749611L;
    static String date = "2023-01-26 19:13:31";
    
    public TimestampUtilTest() {
    }

    @Test
    public void testConvertToData() {
        String result = TimestampUtil.convertTimestampToData(timestamp);
        assertEquals(date, result);
    }

    @Test
    public void testConvertToTimestamp() throws ParseException {
        long result = TimestampUtil.convertDateToTimestamp(date);
        
        System.out.println(timestamp - result);
        assertEquals(timestamp, result);
    }
    
}
