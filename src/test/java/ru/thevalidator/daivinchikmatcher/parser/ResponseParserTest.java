/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.thevalidator.daivinchikmatcher.dto.LongPollServerResponse;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Keyboard;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class ResponseParserTest {
    
    public ResponseParserTest() {
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
//    public void testParseKeyboard() {
//        System.out.println("parseKeyboard");
//        Object data = null;
//        Keyboard expResult = null;
//        Keyboard result = ResponseParser.parseKeyboard(data);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
    @Test
    public void testParseLonPollRespone() throws Exception {
        String response = "{\"ts\": 1732150034,\"updates\":[[3,1631570,1,-91050183],[7,-91050183,1631570,0],[4,1631571,1,-91050183,1673596579,\"Время просмотра анкеты истекло, действие не выполнено.<br><br>Александр, пришли мне свое местоположение и увидишь кто находится рядом\",{\"title\":\" ... \"},{}],[80,8,0],[52,11,-91050183,0],[3,1631571,1,-91050183],[6,-91050183,1631571,0],[80,7,0]]}";
        
        LongPollServerResponse expResult = null;
        LongPollServerResponse result = ResponseParser.parseLonPollRespone(response);
        System.out.println(result.getTs());
        for (List<Object> update : result.getUpdates()) {
            System.out.println("\n");
            for (Object object : update) {
                System.out.println("> " + object);
            }
        }
        //assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
//
//    @Test
//    public void testParseButtons() throws Exception {
//        System.out.println("parseButtons");
//        String data = "";
//        List<Button> expResult = null;
//        List<Button> result = ResponseParser.parseButtons(data);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//    
//    @Test
//    public void parseLongPollResponseToDtoAndBackTest() {
//        String input = "{\"ts\":1699307640,\"updates\":[[3,1630376,1,-91050183],[7,-91050183,1630376,0],[4,1630377,1,-91050183,1673086451,\"? Давай поиграем<br>в рулетку знакомств ?<br><br>1. Подпишись на меня ? [club91050183|@dayvinchik]<br>2. И не отписывайся<br>3. Вернись сюда и нажми<br>«Продолжить»\",{\"emoji\":\"1\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"Продолжить\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"Возможно позже\"},\"color\":\"default\"}]]}},{}],[80,11,0],[52,11,-91050183,0]]}";
//        String output = null;
//        try {
//            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(input);
//            
//            
//            ObjectMapper mapper = new ObjectMapper();
//            output = mapper.writeValueAsString(dto);
//            
//            System.out.println(">> " + input);
//            System.out.println(">> " + output);
//            
//            System.out.println(">>     \"updates\"       only   " + mapper.writeValueAsString(dto.getUpdates()));
//            
//            
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ResponseParserTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        assertEquals(input, output);
//    }  
        

    
}
