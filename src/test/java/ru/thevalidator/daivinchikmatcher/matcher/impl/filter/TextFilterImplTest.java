/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.matcher.impl.filter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.thevalidator.daivinchikmatcher.dto.Profile;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class TextFilterImplTest {
    
    static TextFilterImpl instance = new TextFilterImpl();
    
    public TextFilterImplTest() {
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
    public void testIsFiltered() {
        Profile profile = new Profile("", "", "", "Общение");
        boolean expResult = true;
        boolean result = instance.isFiltered(profile);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testIsFiltered2() {
        Profile profile = new Profile("", "", "", "знакомство");
        boolean expResult = false;
        boolean result = instance.isFiltered(profile);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
}
