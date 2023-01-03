/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.matcher.impl;

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
public class FilterImplTest {

    private static FilterImpl instance;
    String text;
    String text2;

    public FilterImplTest() {
        text = "Victoria, 21, Малоярославец<br>Секретная страница";
        text2 = "Яна, 24, Калуга";
    }

    @BeforeAll
    public static void setUpClass() {
        instance = new FilterImpl();
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
    public void testIsMatched() {
//        System.out.println("isMatched");
//        String text = "";
//        FilterImpl instance = new FilterImpl();
//        boolean expResult = false;
//        boolean result = instance.isMatched(text);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
        String s = "[CASE]-2";
        System.out.println(s.substring(7));
    }

    @Test
    public void testGetName() {
        String result = instance.getName(text);
        assertEquals("Victoria", result);
        
        String result2 = instance.getName(text2);
        assertEquals("Яна", result2);
        
    }

    @Test
    public void testGetAge() {
        String result = instance.getAge(text);
        assertEquals("21", result);
        
        String result2 = instance.getAge(text2);
        assertEquals("24", result2);
    }

    @Test
    public void testGetCity() {
        String result = instance.getCity(text);
        assertEquals("Малоярославец", result);
        
        String result2 = instance.getCity(text2);
        assertEquals("Калуга", result2);
    }

    @Test
    public void testGetText() {
        String result = instance.getText(text);
        assertEquals("<br>Секретная страница", result);
        
        String result2 = instance.getText(text2);
        assertEquals(null, result2);
    }

}
