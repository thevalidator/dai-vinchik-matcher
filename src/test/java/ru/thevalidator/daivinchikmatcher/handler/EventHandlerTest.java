/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class EventHandlerTest {
    
    public EventHandlerTest() {
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
    @DisplayName("GET FLAGS TEST")
    public void testGetFlags() {
        EventHandler instance = new EventHandler(null, null);
        
        int sum = 14;
        Set<Integer> expResult = new HashSet<>(Arrays.asList(2, 4, 8));
        Set<Integer> result = instance.getFlags(sum);
        assertEquals(expResult, result);
        
        sum = 5;
        expResult = new HashSet<>(Arrays.asList(1, 4));
        result = instance.getFlags(sum);
        assertEquals(expResult, result);
        
        sum = 57;
        expResult = new HashSet<>(Arrays.asList(32, 8, 1, 16));
        result = instance.getFlags(sum);
        assertEquals(expResult, result);
    }
    
}
