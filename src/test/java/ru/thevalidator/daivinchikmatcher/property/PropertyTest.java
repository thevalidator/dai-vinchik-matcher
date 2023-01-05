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
        Property result = Property.readFromJson("src/main/resources/properties_test.json");//\src\main\resources
        assertEquals(2, result.getAccounts().size());
        assertEquals(2, result.getUserAgents().size());
        assertEquals(2, result.getProxies().size());
        assertEquals(10, result.getDelay().getBaseDelay());
        assertEquals(15, result.getDelay().getRandomAddedDelay());
        assertEquals("token_1", result.getAccounts().get(0).getToken());
        assertEquals("account 2", result.getAccounts().get(1).getName());
        assertEquals("123.100.189.192", result.getProxies().get(0).getAdress());
        assertEquals(12345, result.getProxies().get(1).getPort());
        assertEquals("useragent value 1", result.getUserAgents().get(0).getValue());
    }
    
//    @Test
//    public void testSaveToJson() {
//        Property prop = new Property();
//        
//        Account acc1 = new Account();
//        acc1.setName("name 1");
//        acc1.setId(1111111);
//        acc1.setToken("token1");
//        Account acc2 = new Account();
//        acc2.setName("name 2");
//        acc2.setId(22222222);
//        acc2.setToken("token2");
//        
//        UserAgent ua1 = new UserAgent();
//        ua1.setName("useragent name 1");
//        ua1.setValue("useragent 1 value");
//        UserAgent ua2 = new UserAgent();
//        ua2.setName("useragent name 2");
//        ua2.setValue("useragent 2 value");
//        
//        Proxy pr1 = new Proxy("123.100.189.192", 3456);
//        Proxy pr2 = new Proxy("144.111.167.88", 12345);
//        
//        Delay delay = new Delay();
//        delay.setBaseDelay(10);
//        delay.setRandomAddedDelay(15);
//        
//        prop.setAccounts(Arrays.asList(acc1, acc2));
//        prop.setUserAgents(Arrays.asList(ua1, ua2));
//        prop.setProxies(Arrays.asList(pr1, pr2));
//        prop.setDelay(delay);
//        
//        Property.saveToJson(prop);
//        
//    }
    
}
