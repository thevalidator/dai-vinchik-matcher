/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class ExceptionUtilTest {
    
    public ExceptionUtilTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetFormattedDescription() {
        try {
            throw new UnsupportedOperationException("TEST EXCEPTION");
        } catch (UnsupportedOperationException e) {
            String result = ExceptionUtil.getFormattedDescription(e);
            System.out.println("START");
            System.out.println(result);
            System.out.println("END");
        }
        
    }
    
}
