/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import ru.thevalidator.daivinchikmatcher.dto.LongPollServerResponse;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Action;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import ru.thevalidator.daivinchikmatcher.parser.ResponseParser;
import ru.thevalidator.daivinchikmatcher.util.ExceptionUtil;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class HandlerImplTest {

    static ObjectMapper mapper;

    public HandlerImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        mapper = new ObjectMapper();
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
    public void testGetStartMessage() {

        String text = "Слишком много лайков за сегодня – ставь Мне нравится только тем, кто тебе действительно нравится. Загляни к нам попозже<br><br>1. Смотреть анкеты.<br>2. Моя анкета.<br>3. Я больше не хочу никого искать.<br>***<br>4. ?? Бот знакомств Дайвинчик в Telegram.";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"1\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"2\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"3\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?? 4\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            HandlerImpl instance = new HandlerImpl(null, null, null);
            String result = instance.getStartMessage(text, buttons);
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    public void testGetStartMessage2() {

        String text = "Мария, 16, Москва";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
            String result = instance.getStartMessage(text, buttons);
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    @DisplayName("profile response get answer test")
    public void testGetAnswerProfile() {

        String responseContent = "{\n"
                + "	\"ts\": 1684646178,\n"
                + "	\"updates\": [\n"
                + "		[\n"
                + "			3,\n"
                + "			1628376,\n"
                + "			1,\n"
                + "			-91050183\n"
                + "		],\n"
                + "		[\n"
                + "			7,\n"
                + "			-91050183,\n"
                + "			1628376,\n"
                + "			0\n"
                + "		],\n"
                + "		[\n"
                + "			4,\n"
                + "			1628377,\n"
                + "			1,\n"
                + "			-91050183,\n"
                + "			1672316909,\n"
                + "			\"Victoria, 21, Малоярославец<br>Секретная страница\",\n"
                + "			{\n"
                + "				\"content_source\": \"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNjY2NDgwMzM4XzQ1NzI0Nzc0OQ==\",\n"
                + "				\"title\": \" ... \",\n"
                + "				\"keyboard\": {\n"
                + "					\"one_time\": false,\n"
                + "					\"buttons\": [\n"
                + "						[\n"
                + "							{\n"
                + "								\"action\": {\n"
                + "									\"type\": \"text\",\n"
                + "									\"payload\": \"1\",\n"
                + "									\"label\": \"??\"\n"
                + "								},\n"
                + "								\"color\": \"positive\"\n"
                + "							},\n"
                + "							{\n"
                + "								\"action\": {\n"
                + "									\"type\": \"text\",\n"
                + "									\"payload\": \"2\",\n"
                + "									\"label\": \"?\"\n"
                + "								},\n"
                + "								\"color\": \"positive\"\n"
                + "							},\n"
                + "							{\n"
                + "								\"action\": {\n"
                + "									\"type\": \"text\",\n"
                + "									\"payload\": \"3\",\n"
                + "									\"label\": \"?\"\n"
                + "								},\n"
                + "								\"color\": \"negative\"\n"
                + "							},\n"
                + "							{\n"
                + "								\"action\": {\n"
                + "									\"type\": \"text\",\n"
                + "									\"payload\": \"4\",\n"
                + "									\"label\": \"?\"\n"
                + "								},\n"
                + "								\"color\": \"default\"\n"
                + "							}\n"
                + "						]\n"
                + "					]\n"
                + "				}\n"
                + "			},\n"
                + "			{\n"
                + "				\"attach1_type\": \"photo\",\n"
                + "				\"attach1\": \"519324877_457239545\"\n"
                + "			}\n"
                + "		],\n"
                + "		[\n"
                + "			80,\n"
                + "			12,\n"
                + "			0\n"
                + "		],\n"
                + "		[\n"
                + "			52,\n"
                + "			11,\n"
                + "			-91050183,\n"
                + "			0\n"
                + "		]\n"
                + "	]\n"
                + "}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }
    
    @Test
    @DisplayName("profile2 response get answer test")
    public void testGetAnswerprofile2() {

        String responseContent = "{\"ts\":1824067397,\"updates\":[[4,1631328,3,-91050183,1673513238,\"3\",{\"title\":\" ... \"},{}],[3,1631328,1,-91050183],[7,-91050183,1631328,0],[4,1631329,1,-91050183,1673513238,\"Солнышко??, 22, Калуга<br>Рост 158?\",{\"emoji\":\"1\",\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNTY3Mjk4NjA2XzQ1NzI4NDA2MQ==\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"519324877_457240910\"}],[80,8,0],[52,11,-91050183,0],[3,1631329,1,-91050183],[6,-91050183,1631329,0],[80,7,0]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    @DisplayName("need subscription response get answer test")
    public void testGetAnswer2() {

        String responseContent = "{\"ts\":1661082387,\"updates\":[[3,1628977,1,-91050183],[7,-91050183,1628977,0],[4,1628978,1,-91050183,1672641002,\"Чтобы продолжить тебе необходимо подписаться на сообщество Дайвинчика ? [club91050183|@dayvinchik].\",{\"emoji\":\"1\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"Продолжить\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"Возможно позже\"},\"color\":\"default\"}]]}},{}],[80,11,0],[52,11,-91050183,0]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("2", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    @DisplayName("empty account response get answer test")
    public void testGetAnswer3() {

        String responseContent = "{\n"
                + "	\"ts\": 1684646243,\n"
                + "	\"updates\": [\n"
                + "		[\n"
                + "			3,\n"
                + "			1628391,\n"
                + "			1,\n"
                + "			-91050183\n"
                + "		],\n"
                + "		[\n"
                + "			7,\n"
                + "			-91050183,\n"
                + "			1628391,\n"
                + "			0\n"
                + "		],\n"
                + "		[\n"
                + "			4,\n"
                + "			1628392,\n"
                + "			1,\n"
                + "			-91050183,\n"
                + "			1672322219,\n"
                + "			\"В твоей анкете совсем нет текста, если ты напишешь немного о себе и кого ищешь, мы сможем лучше подобрать тебе пару.<br><br>1. Продолжить смотреть анкеты и оставить свой профиль пустым.<br>2. Написать что-то в свою анкету.\",\n"
                + "			{\n"
                + "				\"title\": \" ... \",\n"
                + "				\"keyboard\": {\n"
                + "					\"one_time\": false,\n"
                + "					\"buttons\": [\n"
                + "						[\n"
                + "							{\n"
                + "								\"action\": {\n"
                + "									\"type\": \"text\",\n"
                + "									\"payload\": \"1\",\n"
                + "									\"label\": \"1\"\n"
                + "								},\n"
                + "								\"color\": \"default\"\n"
                + "							},\n"
                + "							{\n"
                + "								\"action\": {\n"
                + "									\"type\": \"text\",\n"
                + "									\"payload\": \"2\",\n"
                + "									\"label\": \"2\"\n"
                + "								},\n"
                + "								\"color\": \"default\"\n"
                + "							}\n"
                + "						]\n"
                + "					]\n"
                + "				}\n"
                + "			},\n"
                + "			{}\n"
                + "		],\n"
                + "		[\n"
                + "			80,\n"
                + "			12,\n"
                + "			0\n"
                + "		],\n"
                + "		[\n"
                + "			52,\n"
                + "			11,\n"
                + "			-91050183,\n"
                + "			0\n"
                + "		]\n"
                + "	]\n"
                + "}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    @DisplayName("mutual like response get answer test")
    public void testGetAnswer4() {

        String responseContent = "{\n"
                + "	\"ts\": 1732150034,\n"
                + "	\"updates\": [\n"
                + "		[\n"
                + "			3,\n"
                + "			1629055,\n"
                + "			1,\n"
                + "			-91050183\n"
                + "		],\n"
                + "		[\n"
                + "			7,\n"
                + "			-91050183,\n"
                + "			1629055,\n"
                + "			0\n"
                + "		],\n"
                + "		[\n"
                + "			4,\n"
                + "			1629056,\n"
                + "			1,\n"
                + "			-91050183,\n"
                + "			1672750548,\n"
                + "			\"Есть взаимная симпатия! Добавляй в друзья - vk.com/id386179098<br><br>Марфа, 23, Симферополь<br>Имя настоящее, отчество тоже)<br>Мне 19 лет, я живу в городе Симферополе. У меня среднее экономическое+курсы 1С бухгалтерия, решаю вопрос с работой. <br> С детства хромаю на одну ногу, детская травма, это видно. <br>Играю на фортепьяно, люблю читать, фотографирую на телефон. Люблю готовить)<br>Люблю гулять когда есть настроение и время))) Люблю беседовать на разные темы)\",\n"
                + "			{\n"
                + "				\"content_source\": \"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvMzg2MTc5MDk4XzQ1NzI4ODUwOQ==\",\n"
                + "				\"title\": \" ... \"\n"
                + "			},\n"
                + "			{\n"
                + "				\"attach1_type\": \"photo\",\n"
                + "				\"attach1\": \"519324877_457239856\"\n"
                + "			}\n"
                + "		],\n"
                + "		[\n"
                + "			80,\n"
                + "			11,\n"
                + "			0\n"
                + "		],\n"
                + "		[\n"
                + "			4,\n"
                + "			1629057,\n"
                + "			1,\n"
                + "			-91050183,\n"
                + "			1672750548,\n"
                + "			\"1. Смотреть анкеты.<br>2. Моя анкета.<br>3. Я больше не хочу никого искать.<br>***<br>4. ✈️ Бот знакомств Дайвинчик в Telegram.\",\n"
                + "			{\n"
                + "				\"emoji\": \"1\",\n"
                + "				\"title\": \" ... \",\n"
                + "				\"keyboard\": {\n"
                + "					\"one_time\": false,\n"
                + "					\"buttons\": [\n"
                + "						[\n"
                + "							{\n"
                + "								\"action\": {\n"
                + "									\"type\": \"text\",\n"
                + "									\"payload\": \"1\",\n"
                + "									\"label\": \"1\"\n"
                + "								},\n"
                + "								\"color\": \"positive\"\n"
                + "							},\n"
                + "							{\n"
                + "								\"action\": {\n"
                + "									\"type\": \"text\",\n"
                + "									\"payload\": \"2\",\n"
                + "									\"label\": \"2\"\n"
                + "								},\n"
                + "								\"color\": \"default\"\n"
                + "							},\n"
                + "							{\n"
                + "								\"action\": {\n"
                + "									\"type\": \"text\",\n"
                + "									\"payload\": \"3\",\n"
                + "									\"label\": \"3\"\n"
                + "								},\n"
                + "								\"color\": \"default\"\n"
                + "							},\n"
                + "							{\n"
                + "								\"action\": {\n"
                + "									\"type\": \"text\",\n"
                + "									\"payload\": \"4\",\n"
                + "									\"label\": \"✈️ 4\"\n"
                + "								},\n"
                + "								\"color\": \"default\"\n"
                + "							}\n"
                + "						]\n"
                + "					]\n"
                + "				}\n"
                + "			},\n"
                + "			{}\n"
                + "		],\n"
                + "		[\n"
                + "			52,\n"
                + "			11,\n"
                + "			-91050183,\n"
                + "			0\n"
                + "		]\n"
                + "	]\n"
                + "}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

//    @Test
//    @DisplayName(" response get answer test")
//    public void testGetAnswer5() {
//
//        String responseContent = "";
//
//        try {
//            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
//            HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
//            String result = instance.getAnswerMessage(dto.getUpdates());
//            assertEquals("1", result);
//
//        } catch (JsonProcessingException ex) {
//            System.out.println(ExceptionUtil.getFormattedDescription(ex));
//            fail(ex.getMessage());
//        }
//
//    }

    @Test
    public void testGetFlags() {
        HandlerImpl instance = new HandlerImpl(null, null, null);
        var flags = instance.getFlags(1);
        assertTrue(flags.size() == 1);
        assertTrue(flags.contains(1));
    }

    @Test
    public void testGetFlags2() {
        HandlerImpl instance = new HandlerImpl(null, null, null);
        // friend + unread
        var flags = instance.getFlags(33);
        assertTrue(flags.size() == 2);
        assertTrue(flags.contains(1));
        assertTrue(flags.contains(32));
    }

    @Test
    public void testGetFlags3() {
        HandlerImpl instance = new HandlerImpl(null, null, null);
        var flags = instance.getFlags(512 + 4 + 1 + 32);
        assertTrue(flags.size() == 4);
        assertTrue(flags.contains(512));
        assertTrue(flags.contains(4));
        assertTrue(flags.contains(1));
        assertTrue(flags.contains(32));
    }

    @Test
    public void testGetFlags4() {
        HandlerImpl instance = new HandlerImpl(null, null, null);
        //not delivered + chat
        var flags = instance.getFlags(262144 + 16);
        assertTrue(flags.size() == 2);
        assertTrue(flags.contains(16));
        assertTrue(flags.contains(262144));
    }

    @Test
    public void testGetFlags5() {
        HandlerImpl instance = new HandlerImpl(null, null, null);
        var flags = instance.getFlags(0);
        assertTrue(flags.isEmpty());
    }

    @Test
    public void testGenerateMessageLocationCase() {
        HandlerImpl instance = new HandlerImpl(null, null, null);
        Button b = new Button();
        Action a = new Action();
        a.setLabel("Продолжить");
        a.setPayload("1");
        b.setAction(a);
        List<Button> btns = new ArrayList<>();
        btns.add(b);
        String answer = instance.getStartMessage("Александр, пришли мне свое местоположение и увидишь кто находится рядом", btns);
        System.out.println("answer=" + answer);

    }

//    @Test
//    public void testGetAnswerMessage() {
//        try {
//
//            HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
//            String responseContent = "{\"ts\":1712177712,\"updates\":[[3,1630708,1,-91050183],[6,-91050183,1630708,0],[4,1630709,3,-91050183,1673395507,\"3\",{\"title\":\" ... \"},{}],[80,10,0],[3,1630709,1,-91050183],[7,-91050183,1630709,0],[4,1630710,1,-91050183,1673395508,\"Марина, 21, поселок городского типа Селенгинск<br>???\",{\"emoji\":\"1\",\"content_source\":\"lgECApPOIg9Puc4iD0+5zTBXA8A=\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"519324877_457240614\"}],[80,11,0],[52,11,-91050183,0]]}";
//            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
//            String answer = instance.getAnswerMessage(dto.getUpdates());
//            System.out.println("answer=" + answer);
//
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(HandlerImplTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
