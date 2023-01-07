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
public class CityFilterImplTest {
    
    static CityFilterImpl instance = new CityFilterImpl();
    
    public CityFilterImplTest() {
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
        Profile profile = new Profile("", "", "Калуга", "");
        boolean expResult = false;
        boolean result = instance.isFiltered(profile);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testIsFiltered2() {
        Profile profile = new Profile("", "", "калуга", "");
        boolean expResult = true;
        boolean result = instance.isFiltered(profile);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testIsFiltered3() {
        Profile profile = new Profile("", "", "", "");
        boolean expResult = false;
        boolean result = instance.isFiltered(profile);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testIsFiltered4() {
        Profile profile = new Profile("", "", null, "");
        boolean expResult = false;
        boolean result = instance.isFiltered(profile);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
}
