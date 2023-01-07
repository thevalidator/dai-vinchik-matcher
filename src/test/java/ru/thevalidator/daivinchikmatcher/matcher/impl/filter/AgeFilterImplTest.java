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
public class AgeFilterImplTest {
    
    static AgeFilterImpl instance;
    
    public AgeFilterImplTest() {
        // 16 - 22 age values
    }
    
    @BeforeAll
    public static void setUpClass() {
        instance = new AgeFilterImpl();
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
    public void testIsFilteredAgeLower() {
        Profile profile = new Profile("", "15", "", "");
        boolean expResult = false;
        boolean result = instance.isFiltered(profile);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testIsFilteredAgeHigher() {
        Profile profile = new Profile("", "25", "", "");
        boolean expResult = false;
        boolean result = instance.isFiltered(profile);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testIsFilteredAge16() {
        Profile profile = new Profile("", "16", "", "");
        boolean expResult = true;
        boolean result = instance.isFiltered(profile);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testIsFilteredAge22() {
        Profile profile = new Profile("", "22", "", "");
        boolean expResult = true;
        boolean result = instance.isFiltered(profile);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testIsFilteredAge18() {
        Profile profile = new Profile("", "18", "", "");
        boolean expResult = true;
        boolean result = instance.isFiltered(profile);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
}
