/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import ru.thevalidator.daivinchikmatcher.handler.impl.HandlerImpl;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;
import ru.thevalidator.daivinchikmatcher.handler.Handler;
import ru.thevalidator.daivinchikmatcher.handler.Identifier;
import ru.thevalidator.daivinchikmatcher.handler.ResponseParsingTest;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class HanlerImplTest {

    public HanlerImplTest() {
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
    public void testReadDict() {
        Set<String> dict = HandlerImpl.readDict("src/resources/match_test.dict");
//        for (String s : dict) {
//            System.out.println(s);
//        }

        assertEquals(8, dict.size());
        assertTrue(dict.contains("Калуга"));
    }

}
