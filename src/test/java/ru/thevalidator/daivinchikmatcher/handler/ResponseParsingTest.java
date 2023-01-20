/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.client.actors.UserActorWithoutId;
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
import ru.thevalidator.daivinchikmatcher.dto.LongPollServerResponse;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.KeyboardRoot;
import ru.thevalidator.daivinchikmatcher.handler.impl.HandlerImpl;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import ru.thevalidator.daivinchikmatcher.notification.Informer;
import ru.thevalidator.daivinchikmatcher.parser.ResponseParser;

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
                    List<Button> buttons = root.getKeyboard().getButtons().get(0);
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

    @Test
    public void parseResponseWithoutKEyboardTest() {
        String responseProfileWithoutContetnData = "{\n"
                + "	\"ts\": 1765038625,\n"
                + "	\"updates\": [\n"
                + "		[\n"
                + "			3,\n"
                + "			1628961,\n"
                + "			1,\n"
                + "			-91050183\n"
                + "		],\n"
                + "		[\n"
                + "			7,\n"
                + "			-91050183,\n"
                + "			1628961,\n"
                + "			0\n"
                + "		],\n"
                + "		[\n"
                + "			4,\n"
                + "			1628962,\n"
                + "			1,\n"
                + "			-91050183,\n"
                + "			1672602951,\n"
                + "			\"Есть взаимная симпатия! Добавляй в друзья - vk.com/id450003690<br><br>Тёня, 22, Калуга<br>Общение?\",\n"
                + "			{\n"
                + "				\"emoji\": \"1\",\n"
                + "				\"content_source\": \"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNDUwMDAzNjkwXzQ1NzI0NjMyNw==\",\n"
                + "				\"title\": \" ... \"\n"
                + "			},\n"
                + "			{\n"
                + "				\"attach1_type\": \"photo\",\n"
                + "				\"attach1\": \"519324877_457239822\"\n"
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

        ObjectMapper mapper = new ObjectMapper();
        try {
            LongPollServerResponse responseDTO = mapper.readValue(responseProfileWithoutContetnData, LongPollServerResponse.class);
            for (List<Object> u : responseDTO.getUpdates()) {
                int code = (Integer) u.get(0);

                if (code == 4) {
                    KeyboardRoot root = mapper.convertValue(u.get(6), KeyboardRoot.class);
                    assertNull(root.getKeyboard());
                }
            }

//            Keyboard kbrd = mapper.readValue(responseProfileWithoutContetnData, Keyboard.class);
//            System.out.println(kbrd.getButtons().size());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ResponseParsingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void fullParseResponseWithIndexOutOfBoundsTest() {
        String responseProfileWithoutContetnData = "{\"ts\":1840775934,\"updates\":[[3,4173035,1,-91050183],[6,-91050183,4173035,0],[4,4173043,3,-91050183,1673179103,\"3\",{\"title\":\" ... \"},{}],[80,31,0],[3,4173043,1,-91050183],[7,-91050183,4173043,0],[4,4173044,1,-91050183,1673179103,\"Варя, 15, москва\",{\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNDQ3MDczMDg5XzQ1NzI0NjUyOQ==\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"527463828_457314713\"}],[80,32,0],[52,11,-91050183,0],[4,4173045,2097187,499958552,1673179106,\"Я вроде уже сказал\",{\"title\":\" ... \"},{\"reply\":\"{\\\"conversation_message_id\\\":150}\",\"fwd\":\"0_0\"}],[4,4173046,2097187,499958552,1673179112,\".\",{\"title\":\" ... \"},{\"reply\":\"{\\\"conversation_message_id\\\":134}\",\"fwd\":\"0_0\"}],[3,4173044,1,-91050183],[6,-91050183,4173044,0],[80,31,0],[3,4173046,1,499958552],[3,4173045,1,499958552],[7,499958552,4173046,0],[61,499958552,1],[4,4173047,33,499958552,1673179120,\"окей\",{\"title\":\" ... \"},{}],[80,32,0],[62,499958552,597],[51,382],[52,6,2000000382,775626852],[4,4173048,532481],[2,4173048,131200,2000000382],[4,4173049,532481,2000000382,1673179126,\"Подозрительная активность [id775626852|участника] (Упоминание участников).<br>Блокировка на 1 час в целях безопасности.\",{\"from\":\"-153955265\"},{}],[4,4173050,532481,2000000382,1673179126,\"\",{\"source_act\":\"chat_kick_user\",\"source_mid\":\"775626852\",\"from\":\"-153955265\"},{}],[51,382],[52,8,2000000382,775626852]]}";

        UserActorWithoutId actor = new UserActorWithoutId("23423432rk23kf32pkcl23kf23");
        actor.setUserName("Test username");
        Handler handler = new HandlerImpl(new HashSet<Filter>(), null, actor);
        Informer informer = new Informer();
        informer.registerObserver((String message) -> {
            System.out.println(message);
        });
        ((HandlerImpl) handler).setInformer(informer);
        LongPollServerResponse dto;
        try {
            dto = ResponseParser.parseLonPollRespone(responseProfileWithoutContetnData);
            String answer = handler.getAnswerMessage(dto.getUpdates());
            System.out.println("answer = " + answer);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ResponseParsingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        

//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            LongPollServerResponse responseDTO = mapper.readValue(responseProfileWithoutContetnData, LongPollServerResponse.class);
//            for (List<Object> u : responseDTO.getUpdates()) {
//                int code = (Integer) u.get(0);
//
//                if (code == 4) {
//                    for (Object object : u) {
//                        System.out.println(">>" + object.toString());
//                    }
//                    System.out.println(u.get(6).toString());
//                    KeyboardRoot root = mapper.convertValue(u.get(6), KeyboardRoot.class);
//                    assertNotNull(root.getKeyboard());
//                }
//            }
//
////            Keyboard kbrd = mapper.readValue(responseProfileWithoutContetnData, Keyboard.class);
////            System.out.println(kbrd.getButtons().size());
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ResponseParsingTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @Test
    public void parseButtonsFromLastMsgTest() {
        String input = "[\n"
                + "	{\n"
                + "		\"action\": {\n"
                + "			\"label\": \"1\",\n"
                + "			\"payload\": \"1\",\n"
                + "			\"type\": \"text\"\n"
                + "		},\n"
                + "		\"color\": \"positive\"\n"
                + "	},\n"
                + "	{\n"
                + "		\"action\": {\n"
                + "			\"label\": \"2\",\n"
                + "			\"payload\": \"2\",\n"
                + "			\"type\": \"text\"\n"
                + "		},\n"
                + "		\"color\": \"default\"\n"
                + "	},\n"
                + "	{\n"
                + "		\"action\": {\n"
                + "			\"label\": \"3\",\n"
                + "			\"payload\": \"3\",\n"
                + "			\"type\": \"text\"\n"
                + "		},\n"
                + "		\"color\": \"default\"\n"
                + "	},\n"
                + "	{\n"
                + "		\"action\": {\n"
                + "			\"label\": \"?? 4\",\n"
                + "			\"payload\": \"4\",\n"
                + "			\"type\": \"text\"\n"
                + "		},\n"
                + "		\"color\": \"default\"\n"
                + "	}\n"
                + "]";

        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Button> buttons = mapper.readValue(input, new TypeReference<List<Button>>() {
            });

            System.out.println(buttons.size());
            for (Button b : buttons) {
                System.out.println(b.getAction().getLabel() + " " + b.getAction().getPayload());
            }

        } catch (JsonProcessingException ex) {
            Logger.getLogger(ResponseParsingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void parseResponseToManyAttempts() {
        String input = "[[3,17390,1,-91050183],[6,-91050183,17390,0],"
                + "[4,17391,3,-91050183,1673145351,\"3\",{\"title\":\" ... \"},{}],"
                + "[80,2,0],"
                + "[3,17391,1,-91050183],"
                + "[7,-91050183,17391,0],"
                + "[4,17392,1,-91050183,1673145351,\"Слишком много лайков за сегодня – ставь Мне нравится только тем, кто тебе действительно нравится. Загляни к нам попозже<br><br>1. Смотреть анкеты.<br>2. Моя анкета.<br>3. Я больше не хочу никого искать.<br>***<br>4. ✈️ Бот знакомств Дайвинчик в Telegram.\",{\"emoji\":\"1\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"1\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"2\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"3\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"✈️ 4\"},\"color\":\"default\"}]]}},{}],"
                + "[80,3,0],"
                + "[52,11,-91050183,0]]";

        //TODO:
    }

}
