/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler;

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
import ru.thevalidator.daivinchikmatcher.dto.keyboard.KeyboardRoot;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class ResponseParsingTest {

    public ResponseParsingTest() {
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
    public void parseResponse1Test() {
        String responseProfileWithoutContetnData = "{\"ts\":1690551320,"
                + "\"updates\":["
                + "[3,1628804,1,-91050183],"
                + "[7,-91050183,1628804,0],"
                + "[4,1628805,1,-91050183,1672425412,\"Даша;з, 23, Серпухов\",{\"title\":"
                + "     \" ... \","
                + "     \"keyboard\":{"
                + "         \"one_time\":false,"
                + "         \"buttons\":[["
                + "             {\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},"
                + "             {\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},"
                + "             {\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},"
                + "             {\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}"
                + "         ]]}},"
                + "         {\"attach1_type\":\"photo\",\"attach1\":\"519324877_457239751\"}],"
                + "[80,12,0],"
                + "[52,11,-91050183,0]]}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            LongPollServerResponse responseDTO = mapper.readValue(responseProfileWithoutContetnData, LongPollServerResponse.class);
            for (List<Object> u : responseDTO.getUpdates()) {
                int code = (Integer) u.get(0);
                
                if (code == 4) {
                    KeyboardRoot root = mapper.convertValue(u.get(6), KeyboardRoot.class);
                    List<Button> buttons =  root.getKeyboard().getButtons().get(0);
                    System.out.println(buttons.size());
                    for (Button b : buttons) {
                        System.out.println("action: " + b.getColor());
                        System.out.println("\ttype:" + b.getAction().getType());
                        System.out.println("\tpayload:" + b.getAction().getPayload());
                        System.out.println("\tlabel:" + b.getAction().getLabel());
                    }
                    
                }
            }

//            Keyboard kbrd = mapper.readValue(responseProfileWithoutContetnData, Keyboard.class);
//            System.out.println(kbrd.getButtons().size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ResponseParsingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
