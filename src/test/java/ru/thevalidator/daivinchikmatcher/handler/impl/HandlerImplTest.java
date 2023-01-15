/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.client.actors.UserActorWithoutId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import ru.thevalidator.daivinchikmatcher.notification.Informer;
import ru.thevalidator.daivinchikmatcher.parser.ResponseParser;
import ru.thevalidator.daivinchikmatcher.util.ExceptionUtil;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class HandlerImplTest {

    static ObjectMapper mapper;
    static Informer informer;
    static HandlerImpl instance; 

    public HandlerImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        UserActorWithoutId actor = new UserActorWithoutId("d23d3c98uuj23djc932cjdu92");
        actor.setUserName("Test User");
        instance = new HandlerImpl(new HashSet<>(), null, actor);
        mapper = new ObjectMapper();
        informer = new Informer();
        informer.registerObserver((String message) -> {
            System.out.println(message);
        });
        instance.setInformer(informer);
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
    @DisplayName("Mutual like")
    public void testGetAnswerMulualLikeCase() {

        String responseContent = "{\"ts\":1732150034,\"updates\":[[3,1629055,1,-91050183],[7,-91050183,1629055,0],[4,1629056,1,-91050183,1672750548,\"Есть взаимная симпатия! Добавляй в друзья - vk.com/id386179098<br><br>Марфа, 23, Симферополь<br>Имя настоящее, отчество тоже)<br>Мне 19 лет, я живу в городе Симферополе. У меня среднее экономическое+курсы 1С бухгалтерия, решаю вопрос с работой. <br> С детства хромаю на одну ногу, детская травма, это видно. <br>Играю на фортепьяно, люблю читать, фотографирую на телефон. Люблю готовить)<br>Люблю гулять когда есть настроение и время))) Люблю беседовать на разные темы)\",{\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvMzg2MTc5MDk4XzQ1NzI4ODUwOQ==\",\"title\":\" ... \"},{\"attach1_type\":\"photo\",\"attach1\":\"519324877_457239856\"}],[80,11,0],[4,1629057,1,-91050183,1672750548,\"1. Смотреть анкеты.<br>2. Моя анкета.<br>3. Я больше не хочу никого искать.<br>***<br>4. ✈️ Бот знакомств Дайвинчик в Telegram.\",{\"emoji\":\"1\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"1\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"2\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"3\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"✈️ 4\"},\"color\":\"default\"}]]}},{}],[52,11,-91050183,0]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    @DisplayName("Profile")
    public void testGetAnswerProfileCase() {

        String responseContent = "{\"ts\":1684646234,\"updates\":[[3,1628389,1,-91050183],[7,-91050183,1628389,0],[4,1628390,1,-91050183,1672322118,\"Ирина, 22, Ворсино<br>Приветик, хочу познакомиться пообщаться а там как пойдет общение?.<br>О себе коротко расскажу: <br>Я девочка с характером в которой ещ? дество играет.Мне нравится рисовать, смотреть фильмы и ^~^анимешку.Обажаю животных особенно кошек, так же нравится экспериментировать с выпечкой)).<br>По национальности ??. <br>Из музыки мне нравится Рок, Металл и что-нибудь хорошо звучащее(нравится тусить в хорошей компании,но не получается?). <br>Моя мечта путешествовать по миру и однажды слетать в Японию.???\",{\"emoji\":\"1\",\"content_source\":\"lgECApPODmt2LM4Oa3YszSmMA8A=\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"519324877_457239550\"}],[80,12,0],[52,11,-91050183,0]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    @DisplayName("Advertisements")
    public void testGetAnswerAdvertisementCase() {

        String responseContent = "{\"ts\":1684646234,\"updates\":[[4,1628390,1,-91050183,1672322118,\"Время просмотра анкеты истекло, действие не выполнено.<br><br>Александр, Дайвинчик всегда доступен в Telegram и VK.<br>Ты можешь в один клик перейти к оценке анкет в Telegram и так же быстро вернуться в VK.\",{\"emoji\":\"1\",\"content_source\":\"lgECApPODmt2LM4Oa3YszSmMA8A=\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"Анкеты в Telegram\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"Смотреть анкеты в VK\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"519324877_457239550\"}],[80,12,0],[52,11,-91050183,0]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("2", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    @DisplayName("No text in profile case")
    public void testGetAnswerNoTextInProfilleWarnCase() {

        String responseContent = "{\"ts\":1684646243,\"updates\":[[3,1628391,1,-91050183],[7,-91050183,1628391,0],[4,1628392,1,-91050183,1672322219,\"Время просмотра анкеты истекло, действие не выполнено.<br><br>В твоей анкете совсем нет текста, если ты напишешь немного о себе и кого ищешь, мы сможем лучше подобрать тебе пару.<br><br>1. Продолжить смотреть анкеты и оставить свой профиль пустым.<br>2. Написать что-то в свою анкету.\",{\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"1\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"2\"},\"color\":\"default\"}]]}},{}],[80,12,0],[52,11,-91050183,0]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    @DisplayName("Liked by someone")
    public void testGetAnswerIsLikedBySomeOneCase() {

        String responseContent = "{\"ts\":1824067397,\"updates\": [[4,28385,1,-91050183,1673608136,\"Ты понравилась 1 человеку, показать его?\",{\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"👍\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{}],[52,11,-91050183,0],[3,28385,1,-91050183],[3,28384,1,-91050183],[6,-91050183,28385,0],[80,2,0],[3,28386,1,-91050183],[7,-91050183,28386,0],[80,3,0],[52,11,-91050183,0],[3,28387,1,-91050183],[6,-91050183,28387,0],[80,2,0]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

//    @Test
//    @DisplayName("Too many likes")
//    public void testGetAnswerTooManyLikesCase() {
//
//        String responseContent = "{\"ts\":1867400668,\"updates\":[[3,28920,1,-91050183],[6,-91050183,28920,0],[4,28921,3,-91050183,1673619862,\"1\",{\"title\":\" ... \"},{}],[80,3,0],[3,28921,1,-91050183],[7,-91050183,28921,0],[4,28922,1,-91050183,1673619862,\"Слишком много лайков за сегодня – ставь Мне нравится только тем, кто тебе действительно нравится. Загляни к нам попозже<br><br>1. Смотреть анкеты.<br>2. Моя анкета.<br>3. Я больше не хочу никого искать.<br>***<br>4. ✈️ Бот знакомств Дайвинчик в Telegram.\",{\"emoji\":\"1\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"1\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"2\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"3\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"✈️ 4\"},\"color\":\"default\"}]]}},{}],[80,4,0],[52,11,-91050183,0]]}";
//
//        try {
//            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
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
    @DisplayName("Sleeping state")
    public void testGetAnswerIsSleepingStateCase() {

        String responseContent = "{\"ts\":1867400668,\"updates\":[[3,28920,1,-91050183],[6,-91050183,28920,0],[4,28921,3,-91050183,1673619862,\"1\",{\"title\":\" ... \"},{}],[80,3,0],[3,28921,1,-91050183],[7,-91050183,28921,0],[4,28922,1,-91050183,1673619862,\"1. Смотреть анкеты.<br>2. Моя анкета.<br>3. Я больше не хочу никого искать.<br>***<br>4. ✈️ Бот знакомств Дайвинчик в Telegram.\",{\"emoji\":\"1\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"1\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"2\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"3\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"✈️ 4\"},\"color\":\"default\"}]]}},{}],[80,4,0],[52,11,-91050183,0]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }
//
//    @Test
//    @DisplayName("New profiles want to meet")
//    public void testGetAnswerNewProfilesWantToMeetCase() {
//
//        String responseContent = "{\"ts\":1824067397,\"updates\": }";
//
//        try {
//            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
//            String result = instance.getAnswerMessage(dto.getUpdates());
//            assertEquals("1", result);
//
//        } catch (JsonProcessingException ex) {
//            System.out.println(ExceptionUtil.getFormattedDescription(ex));
//            fail(ex.getMessage());
//        }
//
//    }
//
//    @Test
//    @DisplayName("Location")
//    public void testGetAnswerLocationCase() {
//
//        String responseContent = "{\"ts\":1824067397,\"updates\": }";
//
//        try {
//            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
//            String result = instance.getAnswerMessage(dto.getUpdates());
//            assertEquals("1", result);
//
//        } catch (JsonProcessingException ex) {
//            System.out.println(ExceptionUtil.getFormattedDescription(ex));
//            fail(ex.getMessage());
//        }
//
//    }
//
//    @Test
//    @DisplayName("")
//    public void testGetAnswer() {
//
//        String responseContent = "{\"ts\":1824067397,\"updates\": }";
//
//        try {
//            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
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
    public void testGetStartMessage() {

        String text = "Слишком много лайков за сегодня – ставь Мне нравится только тем, кто тебе действительно нравится. Загляни к нам попозже<br><br>1. Смотреть анкеты.<br>2. Моя анкета.<br>3. Я больше не хочу никого искать.<br>***<br>4. ?? Бот знакомств Дайвинчик в Telegram.";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"1\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"2\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"3\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?? 4\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            //HandlerImpl instance = new HandlerImpl(null, null, null);
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
            //HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
            instance.setInformer(informer);
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
            //HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
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
            //HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
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
            //HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
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
            //HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
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
            //HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    @DisplayName("Location response get answer test")
    public void testGetAnswerLocation() {

        String responseContent = "{\n"
                + "	\"ts\": 1732150034,\n"
                + "	\"updates\":[[3,1631570,1,-91050183],[7,-91050183,1631570,0],[4,1631571,1,-91050183,1673596579,\"Время просмотра анкеты истекло, действие не выполнено.<br><br>Александр, пришли мне свое местоположение и увидишь кто находится рядом\",{\"title\":\" ... \"},{}],[80,8,0],[52,11,-91050183,0],[3,1631571,1,-91050183],[6,-91050183,1631571,0],[80,7,0]]}";

        //String responseContent = "{\"ts\":1824067397,\"updates\":[[4,1631328,3,-91050183,1673513238,\"3\",{\"title\":\" ... \"},{}],[3,1631328,1,-91050183],[7,-91050183,1631328,0],[4,1631329,1,-91050183,1673513238,\"Солнышко??, 22, Калуга<br>Рост 158?\",{\"emoji\":\"1\",\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNTY3Mjk4NjA2XzQ1NzI4NDA2MQ==\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"519324877_457240910\"}],[80,8,0],[52,11,-91050183,0],[3,1631329,1,-91050183],[6,-91050183,1631329,0],[80,7,0]]}";
        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            //HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    @DisplayName("Finish with previous message response get answer test")
    public void testGetAnswerFinishWithPreviousMsg() {

        String responseContent = "{\n"
                + "	\"ts\": 1732150034,\n"
                + "	\"updates\": [[3,4188664,1,-91050183],[6,-91050183,4188664,0],[4,4188665,3,-91050183,1673559687,\"3\",{\"title\":\" ... \"},{}],[80,31,0],[3,4188665,1,-91050183],[7,-91050183,4188665,0],[4,4188666,1,-91050183,1673559688,\"Диана, 17, Москва<br>Общение\",{\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNTgwMjM1NTI4XzQ1NzI0ODU1NA==\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"527463828_457316006\"}],[80,32,0],[52,11,-91050183,0],[4,4188667,1,-91050183,1673559709,\"Нашли кое-кого для тебя ;) Заканчивай с вопросом выше и увидишь кто это\",{\"title\":\" ... \"},{}]]}";

        //String responseContent = "{\"ts\":1824067397,\"updates\":[[4,1631328,3,-91050183,1673513238,\"3\",{\"title\":\" ... \"},{}],[3,1631328,1,-91050183],[7,-91050183,1631328,0],[4,1631329,1,-91050183,1673513238,\"Солнышко??, 22, Калуга<br>Рост 158?\",{\"emoji\":\"1\",\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNTY3Mjk4NjA2XzQ1NzI4NDA2MQ==\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"519324877_457240910\"}],[80,8,0],[52,11,-91050183,0],[3,1631329,1,-91050183],[6,-91050183,1631329,0],[80,7,0]]}";
        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            //HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    @DisplayName("response get answer test")
    public void testGetAnswer1() {

        String responseContent = "{\n"
                + "	\"ts\": 1732150034,\n"
                + "	\"updates\": [[4,28385,1,-91050183,1673608136,\"Ты понравилась 1 человеку, показать его?\",{\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"👍\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{}],[52,11,-91050183,0],[3,28385,1,-91050183],[3,28384,1,-91050183],[6,-91050183,28385,0],[80,2,0],[4,28386,19,-91050183,1673608154,\"👍\",{\"emoji\":\"1\",\"payload\":\"1\",\"title\":\" ... \"},{}],[3,28386,1,-91050183],[7,-91050183,28386,0],[4,28387,1,-91050183,1673608155,\"Кому-то понравилась твоя анкета:<br><br>إبروهيم, 19, Новомосковский административный округ\",{\"content_source\":\"lgECApPOK7Hpic4rsemJzTA9A8A=\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"жалоба\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"400038024_457241118\"}],[80,3,0],[52,11,-91050183,0],[3,28387,1,-91050183],[6,-91050183,28387,0],[80,2,0]]}";

        //String responseContent = "{\"ts\":1824067397,\"updates\":[[4,1631328,3,-91050183,1673513238,\"3\",{\"title\":\" ... \"},{}],[3,1631328,1,-91050183],[7,-91050183,1631328,0],[4,1631329,1,-91050183,1673513238,\"Солнышко??, 22, Калуга<br>Рост 158?\",{\"emoji\":\"1\",\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNTY3Mjk4NjA2XzQ1NzI4NDA2MQ==\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"519324877_457240910\"}],[80,8,0],[52,11,-91050183,0],[3,1631329,1,-91050183],[6,-91050183,1631329,0],[80,7,0]]}";
        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            //HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    @DisplayName(" response get answer test")
    public void testGetAnswer5() {

        String responseContent = "{\n"
                + "	\"ts\": 1732150034,\n"
                + "	\"updates\": [[3,28461,1,-91050183],[6,-91050183,28461,0],[4,28462,3,-91050183,1673608917,\"1\",{\"title\":\" ... \"},{}],[80,3,0],[3,28462,1,-91050183],[7,-91050183,28462,0],[4,28463,1,-91050183,1673608917,\"Roman, 17, Москва<br>если есть ПК с майном и хамачи,то го играть<br>+бравл <br>кто лайкнул,тот первый и пишет кста\",{\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNDI1Mzg0NzE4XzQ1NzI2MjUyNA==\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"400038024_457241152\"}],[80,4,0],[52,11,-91050183,0],[4,28464,1,-91050183,1673608928,\"Нашли кое-кого для тебя ;) Заканчивай с вопросом выше и увидишь кто это\",{\"title\":\" ... \"},{}]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

//    @Test
//    @DisplayName(" response get answer test")
//    public void testGetAnswer6() {
//
//        String responseContent = "{\n"
//                + "	\"ts\": 1732150034,\n"
//                + "	\"updates\": [[3,3257,1,-91050183],[6,-91050183,3257,0],[4,3258,3,-91050183,1673645050,\"3\",{\"title\":\" ... \"},{}],[80,0,0],[3,3258,1,-91050183],[7,-91050183,3258,0],[4,3259,1,-91050183,1673645050,\"-, москва<br>я лиза, называют мефедронщицей🥶 <br>152 ростом, кучерявая, глаза зелёные вроде. <br>люблю хайперпоп, скворе скворе👩‍🍼 <br>смотрю и рисую аниме, довольно общительный и открытый человек, хочется милого мальчика, а вообще хочется мальчика наркомана чтобы холодными вечерами мы лампово пиздились в клубном туалете за дозу...что б он ебал меня в мусоре возле рынка на котором мы продали мою ногу ради дозы героина\",{\"emoji\":\"1\",\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNDAxODU4NDA2XzQ1NzI4ODYxNw==\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"748996382_457240542\"}],[80,1,0],[52,11,-91050183,0],[62,251309954,1],[4,3260,2629633,2000000001,1673645060,\"Спишь?\",{\"from\":\"251309954\",\"mentions\":[328310757],\"marked_users\":[[1,[328310757]]]},{\"reply\":\"{\\\"conversation_message_id\\\":482536}\",\"fwd\":\"0_0\"}],[80,2,0]]}";
//
//        try {
//            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
//            String result = instance.getAnswerMessage(dto.getUpdates());
//            assertEquals("1", result);
//
//        } catch (JsonProcessingException ex) {
//            System.out.println(ExceptionUtil.getFormattedDescription(ex));
//            fail(ex.getMessage());
//        }
//
//    }

//    @Test
//    @DisplayName("No such answer response get answer test")
//    public void testGetAnswer5() {
//      [LPR] {"ts":1867300714,"updates":[[3,1631895,1,-91050183],[7,-91050183,1631895,0],[4,1631896,1,-91050183,1673739308,"Нет такого варианта ответа",{"title":" ... ","keyboard":{"one_time":false,"buttons":[[{"action":{"type":"text","payload":"1","label":"??"},"color":"positive"},{"action":{"type":"text","payload":"2","label":"?"},"color":"positive"},{"action":{"type":"text","payload":"3","label":"?"},"color":"negative"},{"action":{"type":"text","payload":"4","label":"?"},"color":"default"}]]}},{}],[80,8,0],[52,11,-91050183,0],[3,1631896,1,-91050183],[6,-91050183,1631896,0],[80,7,0]]}

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
        //HandlerImpl instance = new HandlerImpl(null, null, null);
        var flags = instance.getFlags(1);
        assertTrue(flags.size() == 1);
        assertTrue(flags.contains(1));
    }

    @Test
    public void testGetFlags2() {
        //HandlerImpl instance = new HandlerImpl(null, null, null);
        // friend + unread
        var flags = instance.getFlags(33);
        assertTrue(flags.size() == 2);
        assertTrue(flags.contains(1));
        assertTrue(flags.contains(32));
    }

    @Test
    public void testGetFlags3() {
        //HandlerImpl instance = new HandlerImpl(null, null, null);
        var flags = instance.getFlags(512 + 4 + 1 + 32);
        assertTrue(flags.size() == 4);
        assertTrue(flags.contains(512));
        assertTrue(flags.contains(4));
        assertTrue(flags.contains(1));
        assertTrue(flags.contains(32));
    }

    @Test
    public void testGetFlags4() {
        //HandlerImpl instance = new HandlerImpl(null, null, null);
        //not delivered + chat
        var flags = instance.getFlags(262144 + 16);
        assertTrue(flags.size() == 2);
        assertTrue(flags.contains(16));
        assertTrue(flags.contains(262144));
    }

    @Test
    public void testGetFlags5() {
        //HandlerImpl instance = new HandlerImpl(null, null, null);
        var flags = instance.getFlags(0);
        assertTrue(flags.isEmpty());
    }

    @Test
    public void testGenerateMessageLocationCase() {
        //HandlerImpl instance = new HandlerImpl(null, null, null);
        Button b = new Button();
        Action a = new Action();
        a.setLabel("Продолжить");
        a.setPayload("1");
        b.setAction(a);
        List<Button> btns = new ArrayList<>();
        btns.add(b);
        String answer = instance.getStartMessage("Александр, пришли мне свое местоположение и увидишь кто находится рядом", btns);
        //System.out.println("answer=" + answer);

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
