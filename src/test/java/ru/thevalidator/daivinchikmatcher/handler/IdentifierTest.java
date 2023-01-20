/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;
import ru.thevalidator.daivinchikmatcher.util.EmojiCleaner;
import ru.thevalidator.daivinchikmatcher.util.ExceptionUtil;

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
        String text = "Ирина, 22, Ворсино<br>Приветик, хочу познакомиться пообщаться а там как пойдет общение?."
                + "<br>О себе коротко расскажу: <br>Я девочка с характером в которой ещ? дество играет.Мне нравит"
                + "ся рисовать, смотреть фильмы и ^~^анимешку.Обажаю животных особенно кошек, так же нравится экс"
                + "периментировать с выпечкой)).<br>По национальности ??. <br>Из музыки мне нравится Рок, Металл "
                + "и что-нибудь хорошо звучащее(нравится тусить в хорошей компании,но не получается?). <br>Моя ме"
                + "чта путешествовать по миру и однажды слетать в Японию.???,";
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
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile2() {
        String text = "Ирина, 22, Ворсино\n"
                + "Приветик, хочу познакомиться пообщаться а там как пойдет общение?.\n"
                + "О себе коротко расскажу: \n"
                + "Я девочка с характером в которой ещ? дество играет.Мне нравится рисовать, смотреть фильмы и ^~^анимешку.Обажаю животных особенно кошек, так же нравится экспериментировать с выпечкой)).\n"
                + "По национальности ??. \n"
                + "Из музыки мне нравится Рок, Металл и что-нибудь хорошо звучащее(нравится тусить в хорошей компании,но не получается?). \n"
                + "Моя мечта путешествовать по миру и однажды слетать в Японию.???";
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
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile3() {
        String text = "Время просмотра анкеты истекло, действие не выполнено.\n"
                + "\n"
                + "🦊~ LиSенOК ~🦊, 23, Калуга\n"
                + "Но у меня с мартини разговор короткий. Я его не... ну, не... очень люб.. ну, пил, но не любл... но люблю... но не много пью его.. но пью";
        String buttonsData = "[{\"action\":{\"label\":\"❤️\",\"payload\":\"1\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"💌\",\"payload\":\"2\",\"type\":\"text\"},\"color\":\"positive\"}, {\"action\":{\"label\":\"👎\",\"payload\":\"3\",\"type\":\"text\"},\"color\":\"negative\"}, {\"action\":{\"label\":\"💤\",\"payload\":\"4\",\"type\":\"text\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile4() {
        String text = ", 15, Москва<br>нужен интересный собеседник, повышенный интеллект и инициатива в счёт.";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile5() {
        //String text = "наташа, 15, Москва\nпривет)\n15 лет, рост 175, стрелец\nучусь в политехе, сплю\nищу новые знакомства поблизости, желательно простых ребят\nобнимаю!";
        String text = "наташа, 15, Москва<br>привет)<br>15 лет, рост 175, стрелец<br>учусь в политехе, сплю<br>ищу новые знакомства поблизости, желательно простых ребят<br>обнимаю!";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile6() {
        String text = ", 16, Москва<br>Костя";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile7() {
        String text = "энвисли, 16, Москва\nосновное чтобы понять пролистывать вам или нет \nиграю в игры(генш, бс, секай, дети света и тд), аниме, ну база короче, люблю чай, чай любит меня, по знаку зодиака телец, хочу чебупиццу, обычно активнее всего я вечером и ночью";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile8() {
        String text = "Никак , 15, Москва 😚<br>Взаимные лайки💗";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile9() {
        String text = "Н0дежда, 15, Москва\nменя задолбал ДВ с тем, чтобы в моей анкете был какой-то текст. Я не знаю, что писать, поэтому напишу какой-то фигни для \"астрологов\" и микрочеликов:\nРост: 172\nЗЗ: лев\nПолная дата рождения:\nВремя рождения: 23.30\nмне скучно";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile10() {
        String text = "Дарья, 18, Москва – район Гольяново.<br>Учусь на повара-кондитера. Ищу новые знакомства ёпт.";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile11() {
        String text = "Мар_ина, 21, поселок городского типа Селенгинск<br>???";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile12() {
        String text = "Vika, 21, Калуга<br>💙💙💙";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile13() {
        String text = "Я Настя, мне 14, милая, весёлая) добрая,, 15, Москва)\nИщу весёлых ребят, так как одной скучно , найдитесь ?\nP.S. Особенно красивых ?";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile14() {
        String text = "?, 16, мск?";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile15() {
        String text = "Таня, 17, Москва (северо-запад)<br>привет ?<br>люблю кошек и в целом всех животных, растения, хороших, уважающих других людей, боба ти и чизкейки <br>??";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile16() {
        //what about symbols <>
        String text = "Та_ня!@#$%^&*()-+=\"|/':;`~№?, 17, Москва (северо-запад)<br>привет ?<br>люблю кошек и в целом всех животных, растения, хороших, уважающих других людей, боба ти и чизкейки <br>??";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile17() {
        String text = "мурк, 16, москва<br>????????????";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile18() {
        String text = "Солнышко❤, 22, Калуга\n"
                + "Рост 158✨";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile19() {
        //String text = "Айта́, 23, Якутск<br>играю в симс и мобильный пабг. спасибо.";
        //Кому-то понравилась твоя анкета:<br><br>إبروهيم, 19, Новомосковский административный округ
        String text = "Айта́, 23, Якутск<br>играю в симс и мобильный пабг. спасибо.";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"??\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"?\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"?\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"?\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile20() {
        String text = "Кому-то понравилась твоя анкета:<br><br>إبروهيم, 19, Новомосковский административный округ";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"жалоба\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsProfile21() {
        String text = "Маша, 15, Москва♡";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void testIsProfile22() {
        String text = "Полина, 17, Москва";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void testIsProfile23() {
        String text = "Полина, 15, 📍Москва";
        String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"❤️\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"💌\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"3\",\"label\":\"👎\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"4\",\"label\":\"💤\"},\"color\":\"default\"}]";

        try {

            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isProfile(EmojiCleaner.clean(text), buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
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
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

    @Test
    public void testIsTelegramInvite() {
        try {

            String text = "Время просмотра анкеты истекло, действие не выполнено.<br><br>Александр, Дайвинчик всегда доступен в Telegram и VK.<br>Ты можешь в один клик перейти к оценке анкет в Telegram и так же быстро вернуться в VK.";
            String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"Анкеты в Telegram\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"Анкеты в VK\"},\"color\":\"default\"}]";
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isAdvertisement(text, buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsLocation() {
//        try {

        String text = "Время просмотра анкеты истекло, действие не выполнено.<br><br>Александр, пришли мне свое местоположение и увидишь кто находится рядом";
        String buttonsData = "[{\"action\":{\"payload\":\"1\",\"label\":\"Продолжить\"}}]";
        List<Button> buttons = null;//mapper.readValue(buttonsData, new TypeReference<List<Button>>() {});
        boolean result = Identifier.isLocation(text, buttons);
        assertTrue(result);

//        } catch (JsonProcessingException ex) {
//            System.out.println(ExceptionUtil.getFormattedDescription(ex));
//            fail(ex.getMessage());
//        }
    }

    @Test
    public void testIsAdvise() {
        try {
            //[4,33446,1,-91050183,1673801926,"Твоя анкета может собирать больше лайков.<br><br>Попробуй изменить фото и описание к анкете.<br><br>1. Перейти к редактированию анкеты.<br>2. Продолжить смотреть анкеты.",{"title":" ... ","keyboard":{"one_time":false,"buttons":[[{"action":{"type":"text","payload":"1","label":"1"},"color":"default"},{"action":{"type":"text","payload":"2","label":"2"},"color":"default"}]]}},{}]
            String text = "Твоя анкета может собирать больше лайков.<br><br>Попробуй изменить фото и описание к анкете.<br><br>1. Перейти к редактированию анкеты.<br>2. Продолжить смотреть анкеты.";
            String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"1\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"2\"},\"color\":\"default\"}]";
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isAdvise(text, buttons);
            assertTrue(result);
        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }

    }

//    @Test
//    public void testIsNeedSubscription() {
//        try {
//            
//            String text = "Чтобы продолжить тебе необходимо подписаться на сообщество Дайвинчика ? [club91050183|@dayvinchik].";
//            String buttonsData = "[{\"action\":{\"type\":\"text\",\"payload\":\"1\",\"label\":\"Продолжить\"},\"color\":\"positive\"},{\"action\":{\"type\":\"text\",\"payload\":\"2\",\"label\":\"Возможно позже\"},\"color\":\"default\"}]";
//            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {});
//            boolean result = Identifier.isNeedSubscription(text, buttons);
//            assertTrue(result);
//
//        } catch (JsonProcessingException ex) {
//            System.out.println(ExceptionUtil.getFormattedDescription(ex));
//            fail(ex.getMessage());
//        }
//    }
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
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsSleeping2() {
        try {

            String text = "1. Смотреть анкеты.<br>2. Моя анкета.<br>3. Я больше не хочу никого искать.<br>***<br>4. ✈️ Бот знакомств Дайвинчик в Telegram.";
            String buttonsData = "[\n"
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
                    + "						]";
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isSleeping(text, buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsLikedBySomeone() {
        try {

            String text = "Ты понравился 1 девушке, показать её";
            String buttonsData = "[\n"
                    + "	{\n"
                    + "		\"action\": {\n"
                    + "			\"type\": \"text\",\n"
                    + "			\"payload\": \"1\",\n"
                    + "			\"label\": \"?\"\n"
                    + "		},\n"
                    + "		\"color\": \"positive\"\n"
                    + "	},\n"
                    + "	{\n"
                    + "		\"action\": {\n"
                    + "			\"type\": \"text\",\n"
                    + "			\"payload\": \"2\",\n"
                    + "			\"label\": \"?\"\n"
                    + "		},\n"
                    + "		\"color\": \"default\"\n"
                    + "	}\n"
                    + "]";
            List<Button> buttons = mapper.readValue(buttonsData, new TypeReference<List<Button>>() {
            });
            boolean result = Identifier.isLikedBySomeone(text, buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
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
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsMutualLike() {
        try {

            String text = "Есть взаимная симпатия! Добавляй в друзья - vk.com/id450003690<br><br>Тёня, 22, Калуга<br>Общение?";
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
            boolean result = Identifier.isMutualLike(text, buttons);
            assertTrue(result);

        } catch (JsonProcessingException ex) {
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
            fail(ex.getMessage());
        }
    }
}
