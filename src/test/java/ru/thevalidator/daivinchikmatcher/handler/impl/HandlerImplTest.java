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
import ru.thevalidator.daivinchikmatcher.gui.AppWindow;
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
        AppWindow window = new AppWindow();
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
    @DisplayName("Start task")
    public void testGetAnswerStartTask() {
        try {
            String lastMsgText = "Алина, 16, москва";
            String kbrdData = "[{\"action\":{\"label\":\"❤️\",\"payload\":\"1\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"💌\",\"payload\":\"2\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"👎\",\"payload\":\"3\",\"type\":\"text\"},\"color\":\"negative\"}, {\"action\":{\"label\":\"💤\",\"payload\":\"4\",\"type\":\"text\"},\"color\":\"default\"}]";
            List<Button> buttons = ResponseParser.parseButtons(kbrdData);
            String result = instance.getStartMessage(lastMsgText, buttons);
            assertEquals("1", result);
        } catch (JsonProcessingException ex) {
            //Logger.getLogger(HandlerImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    @DisplayName("Profile2")
    public void testGetAnswerProfileCase2() {

        String responseContent = "{\"ts\":1785440190,\"updates\":[[3,4216918,1,-91050183],[6,-91050183,4216918,0],[4,4216919,3,-91050183,1674135767,\"3\",{\"title\":\" ... \"},{}],[80,28,0],[3,4216919,1,-91050183],[7,-91050183,4216919,0],[4,4216920,1,-91050183,1674135767,\"Полина, 15, 📍Москва\",{\"emoji\":\"1\",\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNjEyMjMxMDczXzQ1NzI1NTk3Mw==\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"527463828_457319251\"}],[80,29,0],[52,11,-91050183,0]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (Exception ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }
    
    @Test
    @DisplayName("Profile3")
    public void testGetAnswerProfileCase3() {

        String responseContent = "{\"ts\":1836825949,\"updates\":[[3,487,1,-91050183],[6,-91050183,487,0],[4,488,3,-91050183,1674385070,\"3\",{\"title\":\" ... \"},{}],[80,1,0],[3,488,1,-91050183],[7,-91050183,488,0],[4,489,1,-91050183,1674385070,\"Виктория, 24, Москва<br>Мне многое интересно...<br>Музыка, спорт, йога,танцы, рисование, фильмы, сериалы, фестивали, концерты, тусовки прикольные(мастер классы по танцам, джем(импровизированная музыка)), фехтование,стрельба из лука,мастер классы различные и тд. <br>Я не пью, не курю.<br>Хочу пообщаться ✨\",{\"emoji\":\"1\",\"content_source\":\"lgECApPOGMCkbs4YwKRuzcAiA8A=\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"778875451_457239213\"}],[80,2,0],[52,11,-91050183,0],[3,332,1,716059501],[7,716059501,332,0],[61,716059501,1],[4,490,1,716059501,1674385087,\"🙏\",{\"emoji\":\"1\",\"title\":\" ... \"},{}],[80,3,0]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (Exception ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }
    
    @Test
    @DisplayName("Profile4")
    public void testGetAnswerProfileCase4() {

        String responseContent = "{\"ts\":1714379528,\"updates\":[[3,6607,1,-91050183],[6,-91050183,6607,0],[4,6608,3,-91050183,1674396640,\"3\",{\"title\":\" ... \"},{}],[80,24,0],[3,6608,1,-91050183],[7,-91050183,6608,0],[4,6609,1,-91050183,1674396640,\"Дима, 20, Москва<br>Мне 17\",{\"content_source\":\"lgECApPOHMYfwM4cxh\\/AzaHgA8A=\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"364392412_457239140\"}],[80,25,0],[52,11,-91050183,0]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (Exception ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }
    
    @Test
    @DisplayName("Mutual like message")
    public void testGetAnswerMutualLikeCase1() {

        String responseContent = "{\"ts\":1836825567,\"updates\":[[3,398,1,-91050183],[6,-91050183,398,0],[80,0,0],[4,399,3,-91050183,1674384256,\"1\",{\"title\":\" ... \"},{}],[3,399,1,-91050183],[7,-91050183,399,0],[4,400,1,-91050183,1674384257,\"Отлично! Надеюсь хорошо проведете время ;) добавляй в друзья - vk.com\\/id218648185\",{\"title\":\" ... \"},{}],[80,1,0],[4,401,1,-91050183,1674384257,\"Это все, идем дальше?\",{\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"Смотреть анкеты\"},\"color\":\"positive\"}],[{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"Главное меню\"},\"color\":\"default\"}]]}},{}],[52,11,-91050183,0],[3,400,1,-91050183],[6,-91050183,400,1],[3,401,1,-91050183],[6,-91050183,401,0],[80,0,0]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (Exception ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }
    
    @Test
    @DisplayName("Mutual like message 2")
    public void testGetAnswerMutualLikeCase2() {

        String responseContent = "{\"ts\":1625883660,\"updates\":[[3,1179,1,-91050183],[6,-91050183,1179,0],[4,1180,3,-91050183,1674415390,\"1\",{\"title\":\" ... \"},{}],[80,7,0],[3,1180,1,-91050183],[7,-91050183,1180,0],[4,1181,1,-91050183,1674415390,\"Отлично! Надеюсь хорошо проведете время ;) добавляй в друзья - vk.com\\/id603666502\",{\"title\":\" ... \"},{}],[80,8,0],[4,1182,1,-91050183,1674415390,\"Это все, идем дальше?\",{\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"Смотреть анкеты\"},\"color\":\"positive\"}],[{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"Главное меню\"},\"color\":\"default\"}]]}},{}],[52,11,-91050183,0]]}";

        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (Exception ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }
    
    @Test
    @DisplayName("Mutual like message 3")
    public void testGetAnswerMutualLikeCase3() {

        String responseContent = "{\"ts\":1625884417,\"updates\":[[4,1349,3,-91050183,1674417614,\"1\",{\"title\":\" ... \"},{}],[3,1349,1,-91050183],[7,-91050183,1349,0],[4,1350,1,-91050183,1674417614,\"Отлично! Надеюсь хорошо проведете время ;) добавляй в друзья - vk.com\\/id506090967\",{\"title\":\" ... \"},{}],[80,8,0],[4,1351,1,-91050183,1674417615,\"Это все, идем дальше?\",{\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"Смотреть анкеты\"},\"color\":\"positive\"}],[{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"Главное меню\"},\"color\":\"default\"}]]}},{}],[52,11,-91050183,0],[3,1351,1,-91050183],[3,1350,1,-91050183],[6,-91050183,1351,0],[80,7,0]]}";
        try {
            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
            String result = instance.getAnswerMessage(dto.getUpdates());
            assertEquals("1", result);

        } catch (Exception ex) {
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
    
    @Test
    @DisplayName("Liked by someone")
    public void testGetAnswerIsLikedBySomeOneCase2() {

        String responseContent = "{\"ts\":1625884395,\"updates\":[[3,1341,1,-91050183],[7,-91050183,1341,0],[4,1342,1,-91050183,1674417232,\"Ты понравилась 1 парню, показать его?\",{\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"👍\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{}],[80,8,0],[52,11,-91050183,0],[4,1343,1,-91050183,1674417296,\"Нет такого варианта ответа, напиши одну цифру\",{\"title\":\" ... \"},{}],[3,1343,1,-91050183],[3,1342,1,-91050183],[6,-91050183,1343,0],[4,1344,3,-91050183,1674417565,\"1\",{\"title\":\" ... \"},{}],[80,7,0],[3,1344,1,-91050183],[7,-91050183,1344,0],[4,1345,1,-91050183,1674417566,\"Кому-то понравилась твоя анкета(и еще 1):<br><br>🧢, 18, Москва<br>🦋 Ищу интересных людей, общение и т.д <br><br>  • Я ещё пишу музыку, в стилях хоррор, треп металл, Drill, Detroit, Hyperpop, и разрабатываю свой собственный стиль и звучание) <br><br> • На мой Жанр меня вдохновляют : <br><br>Sagath, Fatal - m, НОКТУ, <br>163onmyneck, Heronwater, midix. <br><br> • Ещё у нас есть беседа) в это беседе 👇<br><br>💎 - Любые обсуждения <br>💎 - Единомышленники <br>💎 - общие интересы       <br>💎 - общие увеличения<br>💎 - дружба <br><br>Так же я создал объединение UNICORN MUSIC для музыкантов с топовым саундом, если ты битмейкер или исполнитель, и хочешь с кем то двигаться, пиши мне в личку с пометкой (UNICORN) и кидай свои работы 😁\",{\"emoji\":\"1\",\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNjUyNzY1NDcxXzQ1NzI1MTI3OA==\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"жалоба\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"522772219_457239481\"}],[80,8,0],[52,11,-91050183,0],[3,1345,1,-91050183],[6,-91050183,1345,0],[80,7,0]]}";

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
    @DisplayName("Liked by someone message after profile")
    public void testGetAnswerIsLikedBySomeOneSeparateMessageCase() {

        String responseContent = "{\"ts\":1785441298,\"updates\":[[3,4217159,1,-91050183],[6,-91050183,4217159,0],[4,4217160,3,-91050183,1674138958,\"1\",{\"title\":\" ... \"},{}],[80,30,0],[3,4217160,1,-91050183],[7,-91050183,4217160,0],[4,4217161,1,-91050183,1674138958,\"Надя, 16, Москва<br>Приветик:) <br>Я не из Москвы, просто ищу с кем пообщаться😃\",{\"emoji\":\"1\",\"content_source\":\"lgECApPOJc7G5s4lzsbmzU6OA8A=\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"527463828_457319354\"}],[80,31,0],[52,11,-91050183,0],[4,4217162,1,-91050183,1674138977,\"Кому-то понравилась твоя анкета! Заканчивай с вопросом выше и посмотрим кто это\",{\"title\":\" ... \"},{}]]}";

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
    @Test
    @DisplayName("Location")
    public void testGetAnswerLocationCase() {

        String responseContent = "{\"ts\":1850291255,\"updates\":[[4,1632177,3,-91050183,1674344484,\"3\",{\"title\":\" ... \"},{}],[3,1632177,1,-91050183],[7,-91050183,1632177,0],[4,1632178,1,-91050183,1674344484,\"Александр, пришли мне свое местоположение и увидишь кто находится рядом\",{\"title\":\" ... \"},{}],[80,6,0],[52,11,-91050183,0],[3,1632178,1,-91050183],[6,-91050183,1632178,0],[80,5,0]]}";

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
    @DisplayName("Advise case")
    public void testGetAnswerAdviseCase() {

        String responseContent = "{\"ts\":1824067397,\"updates\": [[3,33445,1,-91050183],[7,-91050183,33445,0],[4,33446,1,-91050183,1673801926,\"Твоя анкета может собирать больше лайков.<br><br>Попробуй изменить фото и описание к анкете.<br><br>1. Перейти к редактированию анкеты.<br>2. Продолжить смотреть анкеты.\",{\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"1\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"2\"},\"color\":\"default\"}]]}},{}],[80,5,0],[52,11,-91050183,0],[62,460732329,9],[4,33447,2629633,2000000009,1673801929,\"дай Аллах тебе сил перед отцом\",{\"from\":\"495279354\",\"mentions\":[354581371],\"marked_users\":[[1,[354581371]]]},{\"reply\":\"{\\\"conversation_message_id\\\":30356}\",\"fwd\":\"0_0\"}],[3,33446,1,-91050183],[6,-91050183,33446,0],[80,4,0],[62,460732329,9],[4,33448,532481,2000000009,1673801932,\"тебе пизда\",{\"from\":\"495279354\"},{}],[4,33449,2629633,2000000009,1673801936,\"Тебе пизда брат\",{\"from\":\"460732329\",\"mentions\":[354581371],\"marked_users\":[[1,[354581371]]]},{\"reply\":\"{\\\"conversation_message_id\\\":30356}\",\"fwd\":\"0_0\"}]]}";

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
    @DisplayName("Someone liked your profile msg after profile")
    public void testGetAnswerSomeoneLikedYourProfileCase() {

        String responseContent = "{\"ts\":1824067397,\"updates\": [[3,5452,1,-91050183],[6,-91050183,5452,0],[4,5455,3,-91050183,1673811983,\"3\",{\"title\":\" ... \"},{}],[80,2,0],[3,5455,1,-91050183],[7,-91050183,5455,0],[4,5456,1,-91050183,1673811983,\"Стасик, 17, Москва<br>Без лишних вопросов, всо в лс\",{\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNTQ5MjQzNTg4XzQ1NzI0NjcxNA==\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"748996382_457241026\"}],[80,3,0],[52,11,-91050183,0],[64,2000000002,[741029110],1,1673811983],[4,5457,2629633,2000000002,1673811984,\"Мам\",{\"from\":\"690698378\",\"mentions\":[709121760],\"marked_users\":[[1,[709121760]]]},{\"reply\":\"{\\\"conversation_message_id\\\":30904}\",\"fwd\":\"0_0\"}],[62,618730625,2],[4,5458,2629633,2000000002,1673811985,\"Ты себе реально жену ищешь?\",{\"from\":\"618730625\",\"mentions\":[741029110],\"marked_users\":[[1,[741029110]]]},{\"reply\":\"{\\\"conversation_message_id\\\":30902}\",\"fwd\":\"0_0\"}],[4,5459,2629633,2000000002,1673811989,\"\",{\"from\":\"741029110\"},{\"attach1_type\":\"doc\",\"attach1\":\"741029110_652774295\",\"attach1_kind\":\"audiomsg\",\"reply\":\"{\\\"conversation_message_id\\\":30903}\",\"attachments\":\"[{\\\"type\\\":\\\"audio_message\\\",\\\"audio_message\\\":{\\\"id\\\":\\\"652774295\\\",\\\"owner_id\\\":\\\"741029110\\\",\\\"duration\\\":4,\\\"waveform\\\":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,8,2,2,16,13,2,16,0,8,31,10,26,11,10,27,0,14,2,8,0,0,0,0,0,0,0,0,0,0,5,0,1,3,0,0,1,13,15,2,9,6,13,3,1,2,19,3,6,2,0,0,0,4,5,6,3,3,5,0,1,10,4,10,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\\\"link_ogg\\\":\\\"https://psv4.userapi.com/s/v1/amsg/R7RV7me-juMUB9A9pszFmV3jKplFx013pnotdSkSAuqSzp-Qz3Rc13CmegSGdQaSmDlD.ogg\\\",\\\"link_mp3\\\":\\\"https://psv4.userapi.com/s/v1/amsg/-ZECsIFjN53AUCGV08kzwuy0fnZkO4sMg6_bVClvv4G4YJGZ8x00NPg_jQilZs01tKgh.mp3\\\",\\\"locale\\\":\\\"\\\",\\\"is_recognized\\\":0,\\\"access_key\\\":\\\"VAXOZBgkMJfSlVWdwWOfx2ZfVXuAfzY09LX7zV4xRwD\\\",\\\"transcript\\\":\\\"Я это на русском можно.\\\",\\\"transcript_state\\\":\\\"done\\\"}}]\",\"attachments_count\":\"1\",\"fwd\":\"0_0\"}],[62,690698378,2],[62,618730625,2],[5,5459,2629633,2000000002,1673811989,\"\",{\"from\":\"741029110\"},{\"attach1_type\":\"doc\",\"attach1\":\"741029110_652774295\",\"attach1_kind\":\"audiomsg\",\"reply\":\"{\\\"conversation_message_id\\\":30903}\",\"attachments\":\"[{\\\"type\\\":\\\"audio_message\\\",\\\"audio_message\\\":{\\\"id\\\":\\\"652774295\\\",\\\"owner_id\\\":\\\"741029110\\\",\\\"duration\\\":4,\\\"waveform\\\":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,8,2,2,16,13,2,16,0,8,31,10,26,11,10,27,0,14,2,8,0,0,0,0,0,0,0,0,0,0,5,0,1,3,0,0,1,13,15,2,9,6,13,3,1,2,19,3,6,2,0,0,0,4,5,6,3,3,5,0,1,10,4,10,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\\\"link_ogg\\\":\\\"https://psv4.userapi.com/s/v1/amsg/R7RV7me-juMUB9A9pszFmV3jKplFx013pnotdSkSAuqSzp-Qz3Rc13CmegSGdQaSmDlD.ogg\\\",\\\"link_mp3\\\":\\\"https://psv4.userapi.com/s/v1/amsg/-ZECsIFjN53AUCGV08kzwuy0fnZkO4sMg6_bVClvv4G4YJGZ8x00NPg_jQilZs01tKgh.mp3\\\",\\\"locale\\\":\\\"\\\",\\\"is_recognized\\\":0,\\\"access_key\\\":\\\"VAXOZBgkMJfSlVWdwWOfx2ZfVXuAfzY09LX7zV4xRwD\\\",\\\"transcript\\\":\\\"Я это на русском можно.\\\",\\\"transcript_state\\\":\\\"done\\\"}}]\",\"attachments_count\":\"1\",\"fwd\":\"0_0\"}],[4,5460,1,-91050183,1673811991,\"Кому-то понравилась твоя анкета! Заканчивай с вопросом выше и посмотрим кто это\",{\"title\":\" ... \"},{}],[4,5461,532481,2000000002,1673811992,\"Тут тусы по репосту 14+\",{\"from\":\"618730625\"},{}],[4,5462,2629633,2000000002,1673811994,\"Даа\",{\"from\":\"741029110\",\"mentions\":[618730625],\"marked_users\":[[1,[618730625]]]},{\"reply\":\"{\\\"conversation_message_id\\\":30906}\",\"fwd\":\"0_0\"}],[62,690698378,2],[52,19,2000000002,1],[19,4938,0],[4,5463,2629633,2000000002,1673811997,\"АХАХАХАХ\",{\"from\":\"690698378\",\"mentions\":[618730625],\"marked_users\":[[1,[618730625]]]},{\"reply\":\"{\\\"conversation_message_id\\\":30908}\",\"fwd\":\"0_0\"}],[62,741029110,2],[62,741029110,2],[52,19,2000000002,1],[19,4938,0],[52,19,2000000002,1],[19,4938,0],[52,19,2000000002,1],[19,4938,0],[62,741029110,2],[4,5464,2629633,2000000002,1673812012,\"Оставь\",{\"from\":\"601753472\",\"mentions\":[741029110],\"marked_users\":[[1,[741029110]]]},{\"reply\":\"{\\\"conversation_message_id\\\":30909}\",\"fwd\":\"0_0\"}],[4,5465,532481,2000000002,1673812014,\"Брат\",{\"from\":\"601753472\"},{}],[10,2000000002,16777216],[2,4938,131200,2000000002],[52,19,2000000002,0],[4,5466,532481,2000000002,1673812015,\"\",{\"from\":\"495279354\"},{\"attach1_call_initiator_id\":\"495279354\",\"attach1_call_receiver_id\":\"2000000703\",\"attach1_call_state\":\"reached\",\"attach1_call_duration\":\"2717\",\"attach1_call_participants\":\"[495279354,709121760,700885990,397386871,756682729,668722446,673107487,284125714,597418209,587606778,541170203,732597811,707685424,688440925,733079945,568771615,574417931,455730361,747258525,244357386,583821919,685244867,606863977,354581371,539550804,400038024,399937,242599152,741029110,745007556,500496742,460732329,339295212,618730625,679713503,672669597]\",\"attach1_call_participants_count\":\"36\",\"attach1_type\":\"call\",\"attach1\":\"495279354_0\",\"attach1_kind\":\"group_call\"}]]}";

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
    public void testGetStartMessage3() {

        String text = "Настя, 19, Москва\nПриветик, ищу активных человечков в мою беседу‼️\nСтрого Москва, строго от 16+ (15ти летние извините, но ответственность на сходках никто за вас брать не хочет, поэтому сорри🥺)\n В беседе вы можете найти как друзей, так и вторую половинку. \nА также, данная беседа предназначена для знакомств, общения, тусовок и просто хорошего времяпровождения. \n\nДля тех кто хочет в беседу просьба, пишите 💌 потому что много попадаются тех, кто не хочет в беседу и просто так лайкнул, и я зря трачу и их и своё время.";
        String buttonsData = "[{\"action\":{\"label\":\"❤️\",\"payload\":\"1\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"💌\",\"payload\":\"2\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"👎\",\"payload\":\"3\",\"type\":\"text\"},\"color\":\"negative\"}, {\"action\":{\"label\":\"💤\",\"payload\":\"4\",\"type\":\"text\"},\"color\":\"default\"}]";

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
    public void testGetStartMessage4() {

        String text = "Боря, 18, 📍500 метров";
        String buttonsData = "[{\"action\":{\"label\":\"❤️\",\"payload\":\"1\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"💌\",\"payload\":\"2\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"👎\",\"payload\":\"3\",\"type\":\"text\"},\"color\":\"negative\"}, {\"action\":{\"label\":\"💤\",\"payload\":\"4\",\"type\":\"text\"},\"color\":\"default\"}]";

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
    public void testGetStartMessage5() {

        String text = "Александр, 21, 📍1км\nЯ - смешной человек.\nУмею играть в компьютерные видео-развлечения и смотреть сериалы.\nДоучиваюсь на последнем курсе и работаю звукорежиссёром в театре.\nСостою в свободных отношениях (да, она в курсе).\nТактильный, полиаморный выблядок.\nРост 187, если это важно.\nКогда-нибудь видели продукты в магазине? Это я их изобрёл.\nЯ никогда не совершал военные преступления в Косово, честно.\nИщу человека.";
        String buttonsData = "[{\"action\":{\"label\":\"❤️\",\"payload\":\"1\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"💌\",\"payload\":\"2\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"👎\",\"payload\":\"3\",\"type\":\"text\"},\"color\":\"negative\"}, {\"action\":{\"label\":\"💤\",\"payload\":\"4\",\"type\":\"text\"},\"color\":\"default\"}]";

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
    public void testGetStartMessage6() {

        String text = "Сергей, 21, 📍1км\nОбщение";
        String buttonsData = "[{\"action\":{\"label\":\"❤️\",\"payload\":\"1\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"💌\",\"payload\":\"2\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"👎\",\"payload\":\"3\",\"type\":\"text\"},\"color\":\"negative\"}, {\"action\":{\"label\":\"💤\",\"payload\":\"4\",\"type\":\"text\"},\"color\":\"default\"}]";

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
    public void testGetStartMessage7() {

        String text = "Денис, 21, 📍1км\nI am not persistent, I am goal-oriented.";
        String buttonsData = "[{\"action\":{\"label\":\"❤️\",\"payload\":\"1\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"💌\",\"payload\":\"2\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"👎\",\"payload\":\"3\",\"type\":\"text\"},\"color\":\"negative\"}, {\"action\":{\"label\":\"💤\",\"payload\":\"4\",\"type\":\"text\"},\"color\":\"default\"}]";

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
    public void testGetStartMessage8() {

        String text = "Кому-то понравилась твоя анкета:\n\nKemran, 20, 📍2км";
        String buttonsData = "[{\"action\":{\"label\":\"❤️\",\"payload\":\"1\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"👎\",\"payload\":\"2\",\"type\":\"text\"},\"color\":\"negative\"}, {\"action\":{\"label\":\"жалоба\",\"payload\":\"3\",\"type\":\"text\"},\"color\":\"default\"}, {\"action\":{\"label\":\"💤\",\"payload\":\"4\",\"type\":\"text\"},\"color\":\"default\"}]";
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
    public void testGetStartMessage9() {

        String text = "Уже не актуально :(\n\n1. Смотреть анкеты.\n2. Моя анкета.\n3. Я больше не хочу никого искать.\n***\n4. ✈️ Бот знакомств Дайвинчик в Telegram.";
        String buttonsData = "[{\"action\":{\"label\":\"1\",\"payload\":\"1\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"2\",\"payload\":\"2\",\"type\":\"text\"},\"color\":\"default\"}, {\"action\":{\"label\":\"3\",\"payload\":\"3\",\"type\":\"text\"},\"color\":\"default\"}, {\"action\":{\"label\":\"✈️ 4\",\"payload\":\"4\",\"type\":\"text\"},\"color\":\"default\"}]";
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
    
//    @Test
//    public void testGetStartMessage4() {
//
//        String text = "Боря, 18, 📍500 метров";
//        String buttonsData = "[{\"action\":{\"label\":\"❤️\",\"payload\":\"1\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"💌\",\"payload\":\"2\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"👎\",\"payload\":\"3\",\"type\":\"text\"},\"color\":\"negative\"}, {\"action\":{\"label\":\"💤\",\"payload\":\"4\",\"type\":\"text\"},\"color\":\"default\"}]";
//
//        try {
//
//            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
//            });
//            //HandlerImpl instance = new HandlerImpl(new HashSet<>(), null, null);
//            instance.setInformer(informer);
//            String result = instance.getStartMessage(text, buttons);
//            assertEquals("1", result);
//
//        } catch (JsonProcessingException ex) {
//            System.out.println(ExceptionUtil.getFormattedDescription(ex));
//            fail(ex.getMessage());
//        }
//
//    }

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
    @DisplayName("profile3 response get answer test")
    public void testGetAnswerprofile3() {

        String responseContent = "{\"ts\":1824067397,\"updates\":[[4,1631328,3,-91050183,1673513238,\"3\",{\"title\":\" ... \"},{}],[3,1631328,1,-91050183],[7,-91050183,1631328,0],[4,1631329,1,-91050183,1673513238,\"Боря, 18, 📍500 метров\",{\"emoji\":\"1\",\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNTY3Mjk4NjA2XzQ1NzI4NDA2MQ==\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"519324877_457240910\"}],[80,8,0],[52,11,-91050183,0],[3,1631329,1,-91050183],[6,-91050183,1631329,0],[80,7,0]]}";

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
    
    @Test
    @DisplayName("Test 1")
    public void testGetAnswerCase1() {

        String responseContent = "{\"ts\":1742909415,\"updates\":[[3,45222,1,-91050183],[7,-91050183,45222,0],[4,45223,1,-91050183,1673976781,\"Александр, 15, Москва<br>Иисус с диабетом, который рисует людей и играет на гитаре.<br><br>Есть желание пройтись с новым человеком и душевно провести время.<br><br>Метро Бабушкинская\",{\"content_source\":\"lgECApPOFEyxcc4UTLFxzQ6TA8A=\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"400038024_457242237\"}],[80,3,0],[52,11,-91050183,0],[3,45223,1,-91050183],[6,-91050183,45223,0],[80,2,0],[51,8],[52,6,2000000008,421447233],[4,45224,532481,2000000008,1673976794,\"\",{\"source_act\":\"chat_invite_user\",\"source_mid\":\"421447233\",\"from\":\"421447233\"},{}]]}";

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
//    @DisplayName("Test 2")
//    public void testGetAnswerCase2() {
//
//        String responseContent = "{\"ts\":1742910073,\"updates\":[[4,45236,532481,2000000008,1673976850,\"\",{\"from\":\"421447233\"},{\"attach1_product_id\":\"1326\",\"attach1_type\":\"sticker\",\"attach1\":\"63557\",\"attach1_kind\":\"animation\",\"attachments\":\"[{\\\"type\\\":\\\"sticker\\\",\\\"sticker\\\":{\\\"images\\\":[{\\\"height\\\":64,\\\"url\\\":\\\"https:\\/\\/vk.com\\/sticker\\/1-63557-64\\\",\\\"width\\\":64},{\\\"height\\\":128,\\\"url\\\":\\\"https:\\/\\/vk.com\\/sticker\\/1-63557-128\\\",\\\"width\\\":128},{\\\"height\\\":256,\\\"url\\\":\\\"https:\\/\\/vk.com\\/sticker\\/1-63557-256\\\",\\\"width\\\":256},{\\\"height\\\":352,\\\"url\\\":\\\"https:\\/\\/vk.com\\/sticker\\/1-63557-352\\\",\\\"width\\\":352},{\\\"height\\\":512,\\\"url\\\":\\\"https:\\/\\/vk.com\\/sticker\\/1-63557-512\\\",\\\"width\\\":512}],\\\"images_with_background\\\":[{\\\"height\\\":64,\\\"url\\\":\\\"https:\\/\\/vk.com\\/sticker\\/1-63557-64b\\\",\\\"width\\\":64},{\\\"height\\\":128,\\\"url\\\":\\\"https:\\/\\/vk.com\\/sticker\\/1-63557-128b\\\",\\\"width\\\":128},{\\\"height\\\":256,\\\"url\\\":\\\"https:\\/\\/vk.com\\/sticker\\/1-63557-256b\\\",\\\"width\\\":256},{\\\"height\\\":352,\\\"url\\\":\\\"https:\\/\\/vk.com\\/sticker\\/1-63557-352b\\\",\\\"width\\\":352},{\\\"height\\\":512,\\\"url\\\":\\\"https:\\/\\/vk.com\\/sticker\\/1-63557-512b\\\",\\\"width\\\":512}],\\\"product_id\\\":1326,\\\"sticker_id\\\":63557,\\\"animation_url\\\":\\\"https:\\/\\/vk.com\\/sticker\\/3-63557.json\\\",\\\"animations\\\":[{\\\"type\\\":\\\"light\\\",\\\"url\\\":\\\"https:\\/\\/vk.com\\/sticker\\/3-63557.json\\\"},{\\\"type\\\":\\\"dark\\\",\\\"url\\\":\\\"https:\\/\\/vk.com\\/sticker\\/3-63557b.json\\\"}]}}]\",\"attachments_count\":\"1\"}],[62,467700382,8],[4,45237,2629633,2000000008,1673976863,\"…\",{\"from\":\"467700382\",\"mentions\":[421447233],\"marked_users\":[[1,[421447233]]]},{\"reply\":\"{\\\"conversation_message_id\\\":44017}\",\"fwd\":\"0_0\"}],[62,467700382,8],[4,45238,532481,2000000008,1673976867,\"я кушать хочу\",{\"from\":\"467700382\"},{}]]}";
//
//        try {
//            LongPollServerResponse dto = ResponseParser.parseLonPollRespone(responseContent);
//            String result = instance.getAnswerMessage(dto.getUpdates());
//            
//            assertEquals("1", result);
//
//        } catch (JsonProcessingException ex) {
//            System.out.println(ExceptionUtil.getFormattedDescription(ex));
//            fail(ex.getMessage());
//        }
//
//    }
    
    @Test
    @DisplayName("Test 3 Someone liked your profile need to like too")
    public void testGetAnswerCase3() {

        String responseContent = "{\"ts\":1742915429,\"updates\":[[4,46461,3,-91050183,1673987374,\"1\",{\"title\":\" ... \"},{}],[3,46461,1,-91050183],[7,-91050183,46461,0],[4,46462,1,-91050183,1673987374,\"Кому-то понравилась твоя анкета:<br><br>jayzedoff, 17, Москва<br>твич: jayzedoff\",{\"content_source\":\"lgEBAsAD2SZodHRwOi8vdmsuY29tL3Bob3RvNTM5ODg2NzQxXzQ1NzI0NjYwNg==\",\"title\":\" ... \",\"keyboard\":{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"жалоба\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]]}},{\"attach1_type\":\"photo\",\"attach1\":\"400038024_457242443\"}],[80,3,0],[52,11,-91050183,0],[3,46462,1,-91050183],[6,-91050183,46462,0],[80,2,0]]}";

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
