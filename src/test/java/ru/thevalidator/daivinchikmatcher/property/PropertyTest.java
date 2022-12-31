/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class PropertyTest {
    
    public PropertyTest() {
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
    public void testReadFromJson() {
        
        Property result = Property.readFromJson("src/resources/properties_test.json");
        assertEquals(2, result.getAccounts().size());
        assertEquals(2, result.getUserAgents().size());
        assertEquals(2, result.getProxies().size());
        assertEquals(10, result.getDelay().getBaseDelay());
        assertEquals(15, result.getDelay().getRandomAddedDelay());
        assertEquals("token_1", result.getAccounts().get(0).getToken());
        assertEquals("account 2", result.getAccounts().get(1).getName());
        assertEquals("xxx.xxx.xxx.xxx", result.getProxies().get(0).getAdress());
    }
    
}
