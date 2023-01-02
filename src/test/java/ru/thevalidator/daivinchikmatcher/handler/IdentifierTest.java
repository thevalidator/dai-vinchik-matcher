/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class IdentifierTest {

    static ObjectMapper mapper;

    public IdentifierTest() {
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
    public void testIsProfile() {
        String text = "\"Ирина, 22, Ворсино<br>Приветик, хочу познакомиться пообщаться а там как пойдет общение?."
                + "<br>О себе коротко расскажу: <br>Я девочка с характером в которой ещ? дество играет.Мне нравит"
                + "ся рисовать, смотреть фильмы и ^~^анимешку.Обажаю животных особенно кошек, так же нравится экс"
                + "периментировать с выпечкой)).<br>По национальности ??. <br>Из музыки мне нравится Рок, Металл "
                + "и что-нибудь хорошо звучащее(нравится тусить в хорошей компании,но не получается?). <br>Моя ме"
                + "чта путешествовать по миру и однажды слетать в Японию.???\",";
        String buttonsData = "[\n"
                + "	{\n"
                + "		\"action\": {\n"
                + "			\"type\": \"text\",\n"
                + "			\"payload\": \"1\",\n"
                + "			\"label\": \"??\"\n"
                + "		},\n"
                + "		\"color\": \"positive\"\n"
                + "	},\n"
                + "	{\n"
                + "		\"action\": {\n"
                + "			\"type\": \"text\",\n"
                + "			\"payload\": \"2\",\n"
                + "			\"label\": \"?\"\n"
                + "		},\n"
                + "		\"color\": \"positive\"\n"
                + "	},\n"
                + "	{\n"
                + "		\"action\": {\n"
                + "			\"type\": \"text\",\n"
                + "			\"payload\": \"3\",\n"
                + "			\"label\": \"?\"\n"
                + "		},\n"
                + "		\"color\": \"negative\"\n"
                + "	},\n"
                + "	{\n"
                + "		\"action\": {\n"
                + "			\"type\": \"text\",\n"
                + "			\"payload\": \"4\",\n"
                + "			\"label\": \"?\"\n"
                + "		},\n"
                + "		\"color\": \"default\"\n"
                + "	}\n"
                + "]";

        try {
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(text, buttons);
            assertTrue(result);

//            System.out.println("res = " + result);
//            boolean isHeader = "Ирина, 22, Ворсино<br>Приветик, хочу познакомиться пообщаться а там как пойдет общение?.<br>О себе к"
//                    .matches(".+, \\d+, .+(<br>.+)?");
//            System.out.println("header " + isHeader);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ResponseParsingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsNoTextInProfileWarn() {
        try {
            String text = "В твоей анкете совсем нет текста, если ты напишешь немного о себе и кого ищешь, мы сможем лучше подобрать тебе пару.<br><br>1. Продолжить смотреть анкеты и оставить свой профиль пустым.<br>2. Написать что-то в свою анкету.";
            String buttonsData = "[\n"
                    + "	{\n"
                    + "		\"action\": {\n"
                    + "			\"type\": \"text\",\n"
                    + "			\"payload\": \"1\",\n"
                    + "			\"label\": \"1\"\n"
                    + "		},\n"
                    + "		\"color\": \"default\"\n"
                    + "	},\n"
                    + "	{\n"
                    + "		\"action\": {\n"
                    + "			\"type\": \"text\",\n"
                    + "			\"payload\": \"2\",\n"
                    + "			\"label\": \"2\"\n"
                    + "		},\n"
                    + "		\"color\": \"default\"\n"
                    + "	}\n"
                    + "]";
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isNoTextInProfileWarn(text, buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(IdentifierTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testIsExpired() {
        try {
            String text = "Время просмотра анкеты истекло, действие не выполнено.<br><br>Александр, Дайвинчик всегда доступен в Telegram и VK.<br>Ты можешь в один клик перейти к оценке анкет в Telegram и так же быстро вернуться в VK.";
            String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"Анкеты в Telegram\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"Анкеты в VK\"},\"color\":\"default\"}]";
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isExpired(text, buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(IdentifierTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsLocation() {
        try {
            String text = "";
            String buttonsData = "";
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isLocation(text, buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(IdentifierTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsNeedSubscription() {
        try {
            String text = "Чтобы продолжить тебе необходимо подписаться на сообщество Дайвинчика ? [club91050183|@dayvinchik].";
            String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"Продолжить\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"Возможно позже\"},\"color\":\"default\"}]";
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isNeedSubscription(text, buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(IdentifierTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsQuestion() {
        try {
            String text = "Александр, хочешь больше взаимок?<br>Жми ? и спроси что-либо интересное у девушки.<br>Она обязательно ответит ?";
            String buttonsData = "[\n"
                    + "	{\n"
                    + "		\"action\": {\n"
                    + "			\"type\": \"text\",\n"
                    + "			\"payload\": \"1\",\n"
                    + "			\"label\": \"Как это работает?\"\n"
                    + "		},\n"
                    + "		\"color\": \"positive\"\n"
                    + "	},\n"
                    + "	{\n"
                    + "		\"action\": {\n"
                    + "			\"type\": \"text\",\n"
                    + "			\"payload\": \"2\",\n"
                    + "			\"label\": \"Хороший совет\"\n"
                    + "		},\n"
                    + "		\"color\": \"default\"\n"
                    + "	}\n"
                    + "]";
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isQuestion(text, buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(IdentifierTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsSleeping() {
        try {
            String text = "1. Смотреть анкеты.<br>2. Моя анкета.<br>3. Я больше не хочу никого искать.<br>***<br>4. ?? Бот знакомств Дайвинчик в Telegram.";
            String buttonsData = "[\n"
                    + "	{\n"
                    + "		\"action\": {\n"
                    + "			\"type\": \"text\",\n"
                    + "			\"payload\": \"1\",\n"
                    + "			\"label\": \"1\"\n"
                    + "		},\n"
                    + "		\"color\": \"positive\"\n"
                    + "	},\n"
                    + "	{\n"
                    + "		\"action\": {\n"
                    + "			\"type\": \"text\",\n"
                    + "			\"payload\": \"2\",\n"
                    + "			\"label\": \"2\"\n"
                    + "		},\n"
                    + "		\"color\": \"default\"\n"
                    + "	},\n"
                    + "	{\n"
                    + "		\"action\": {\n"
                    + "			\"type\": \"text\",\n"
                    + "			\"payload\": \"3\",\n"
                    + "			\"label\": \"3\"\n"
                    + "		},\n"
                    + "		\"color\": \"default\"\n"
                    + "	},\n"
                    + "	{\n"
                    + "		\"action\": {\n"
                    + "			\"type\": \"text\",\n"
                    + "			\"payload\": \"4\",\n"
                    + "			\"label\": \"?? 4\"\n"
                    + "		},\n"
                    + "		\"color\": \"default\"\n"
                    + "	}\n"
                    + "]";
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isSleeping(text, buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(IdentifierTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsLikedBySomeone() {
        try {
            String text = "";
            String buttonsData = "";
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isLikedBySomeone(text, buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(IdentifierTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsNewProfilesWantToMeet() {
        try {
            String text = "хотят познакомиться с тобой прямо сейчас";
            String buttonsData = "[\n"
                    + "	{\n"
                    + "		\"action\": {\n"
                    + "			\"type\": \"text\",\n"
                    + "			\"payload\": \"1\",\n"
                    + "			\"label\": \"Посмотреть\"\n"
                    + "		},\n"
                    + "		\"color\": \"positive\"\n"
                    + "	},\n"
                    + "	{\n"
                    + "		\"action\": {\n"
                    + "			\"type\": \"text\",\n"
                    + "			\"payload\": \"2\",\n"
                    + "			\"label\": \"Не интересно\"\n"
                    + "		},\n"
                    + "		\"color\": \"default\"\n"
                    + "	}\n"
                    + "]";
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isNewProfilesWantToMeet(text, buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(IdentifierTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsMutualLike() {
        try {
            String text = "Есть взаимная симпатия! Добавляй в друзья - vk.com/id450003690<br><br>Тёня, 22, Калуга<br>Общение?";
            String buttonsData = "";
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isMutualLike(text, buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(IdentifierTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
