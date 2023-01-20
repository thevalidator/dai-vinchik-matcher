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
import ru.thevalidator.daivinchikmatcher.property.Data;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class FileUtilTest {
    
    public FileUtilTest() {
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

//    @Test
//    public void testReadDict() {
//        System.out.println("readDict");
//        String path = "";
//        Set<String> expResult = null;
//        Set<String> result = FileUtil.readDict(path);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testReadAgeDict() {
//        System.out.println("readAgeDict");
//        String path = "";
//        int[] expResult = null;
//        int[] result = FileUtil.readAgeDict(path);
//        assertArrayEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }

    @Test
    public void testReadDelay() {
        String path = Data.DELAY;
        String expResult = "25";
        String result = FileUtil.readDelay(path);
        assertEquals(expResult, result);
    }
    
}
