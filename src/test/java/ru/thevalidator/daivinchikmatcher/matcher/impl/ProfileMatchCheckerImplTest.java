/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.matcher.impl;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static ru.thevalidator.daivinchikmatcher.handler.Identifier.REGEXP;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class ProfileMatchCheckerImplTest {

    static ProfileMatchCheckerImpl instance;
    static final Pattern pattern = Pattern.compile(REGEXP);
    static Matcher matcher;
    String text;
    String text2;
    String text3;
    String text4;

    public ProfileMatchCheckerImplTest() {
        text = "Victoria, 21, Малоярославец<br>Секретная страница";
        text2 = "Яна, 24, Калуга";
        text3 = "Маша, 15, Москва♡";
        text4 = "Кому-то понравилась твоя анкета:<br><br>♡, 19, Новомосковский административный округ<br>привет ?<br>люблю кошек и в целом всех животных";
    }

    @BeforeAll
    public static void setUpClass() {
        instance = new ProfileMatchCheckerImpl(new HashSet<>());
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

//    @Test
//    public void testIsMatched() {
////        System.out.println("isMatched");
////        String text = "";
////        ProfileMatchCheckerImpl instance = new ProfileMatchCheckerImpl();
////        boolean expResult = false;
////        boolean result = instance.isMatched(text);
////        assertEquals(expResult, result);
////        fail("The test case is a prototype.");
//
////        String s = "[CASE]-2";
////        System.out.println(s.substring(7));
//    }

    @Test
    public void testGetName() {
        matcher = pattern.matcher(text);
        matcher.find();
        String result = instance.getName(matcher);
        assertEquals("Victoria", result);
        
        matcher = pattern.matcher(text2);
        matcher.find();
        String result2 = instance.getName(matcher);
        assertEquals("Яна", result2);
        
        matcher = pattern.matcher(text3);
        matcher.find();
        String result3 = instance.getName(matcher);
        assertEquals("Маша", result3);
        
        matcher = pattern.matcher(text4);
        matcher.find();
        String result4 = instance.getName(matcher);
        assertEquals("♡", result4);
        
    }

    @Test
    public void testGetAge() {
        matcher = pattern.matcher(text);
        matcher.find();
        String result = instance.getAge(matcher);
        assertEquals("21", result);
        
        matcher = pattern.matcher(text2);
        matcher.find();
        String result2 = instance.getAge(matcher);
        assertEquals("24", result2);
        
        matcher = pattern.matcher(text3);
        matcher.find();
        String result3 = instance.getAge(matcher);
        assertEquals("15", result3);
        
        matcher = pattern.matcher(text4);
        matcher.find();
        String result4 = instance.getAge(matcher);
        assertEquals("19", result4);
    }

    @Test
    public void testGetCity() {
        matcher = pattern.matcher(text);
        matcher.find();
        String result = instance.getCity(matcher);
        assertEquals("малоярославец", result);
        
        matcher = pattern.matcher(text2);
        matcher.find();
        String result2 = instance.getCity(matcher);
        assertEquals("калуга", result2);
        
        matcher = pattern.matcher(text3);
        matcher.find();
        String result3 = instance.getCity(matcher);
        assertEquals("москва", result3);
        
        matcher = pattern.matcher(text4);
        matcher.find();
        String result4 = instance.getCity(matcher);
        assertEquals("новомосковский административный округ", result4);
    }

    @Test
    public void testGetText() {
        matcher = pattern.matcher(text);
        matcher.find();
        String result = instance.getText(matcher);
        assertEquals("<br>Секретная страница", result);
        
        matcher = pattern.matcher(text2);
        matcher.find();
        String result2 = instance.getText(matcher);
        assertEquals("", result2);
        
        matcher = pattern.matcher(text3);
        matcher.find();
        String result3 = instance.getText(matcher);
        assertEquals("", result3);
        
        matcher = pattern.matcher(text4);
        matcher.find();
        String result4 = instance.getText(matcher);
        assertEquals("<br>привет ?<br>люблю кошек и в целом всех животных", result4);
    }

}
