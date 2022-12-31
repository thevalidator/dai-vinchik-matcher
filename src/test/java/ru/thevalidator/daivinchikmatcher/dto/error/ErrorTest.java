/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.dto.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ErrorTest {

    public ErrorTest() {
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
    public void testJsonToError() {
        String json = "{\"error\":{\"error_code\":14,\"error_msg\":\"Captcha needed\",\"request_params\":[{\"key\":\"method\",\"value\":\"messages.send\"},{\"key\":\"oauth\",\"value\":\"1\"},{\"key\":\"v\",\"value\":\"5.131\"},{\"key\":\"random_id\",\"value\":\"1599556485\"},{\"key\":\"peer_id\",\"value\":\"-91050183\"}],\"captcha_sid\":\"633140547106\",\"captcha_img\":\"https:\\/\\/api.vk.com\\/captcha.php?sid=633140547106&s=1\"}}";
        ObjectMapper om = new ObjectMapper();
        try {
            //Error error = om.readValue(json, Error.class);
            ErrorRoot root = om.readValue(json, ErrorRoot.class);
            System.out.println("code: " + root.getError().getErrorCode());
            System.out.println("captcha sid: " + root.getError().getCaptchaSid());
            System.out.println("captcha img: " + root.getError().getCaptchaImg());
            System.out.println("params: " + root.getError().getRequestParams().size());
            for (RequestParam p : root.getError().getRequestParams()) {
                System.out.println("\tkey: " + p.getKey() + " value: " + p.getValue());
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ErrorTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
