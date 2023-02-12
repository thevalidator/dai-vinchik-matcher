/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.thevalidator.daivinchikmatcher.util.EmojiCleaner;
import ru.thevalidator.daivinchikmatcher.util.SoundUtil;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class CustomTest {

    public CustomTest() {
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
    public void testPlayAlert() {
        SoundUtil.playAlarm();
    }

    @Test
    public void testPlayNotification() {
        SoundUtil.playNotification();
    }

    @Test
    public void testChatAtIndex() {
        String text = "{\"failed\":1,\"ts\":12345678}";
        System.out.println(text.charAt(10));
        System.out.println(text.substring(17, text.length() - 1));
    }

    @Test
    public void testStringRemoveLines() {
        //String text = "Время просмотра анкеты истекло, действие не выполнено.<br><br>В твоей анкете совсем нет текста";
        String text = "Время просмотра анкеты истекло, действие не выполнено.\n\nВ твоей анкете совсем нет текста";
        if (text.startsWith("Время просмотра анкеты истекло")) {
            text = text.replaceFirst("Время просмотра анкеты истекло, действие не выполнено.(<br><br>|\n\n)", "");
        }

        System.out.println(text);
    }

    @Test
    public void testExtractTs() {
        String responseContent = "{\"failed\":1,\"ts\":1659999797}\n";
        String ts = null;
        System.out.println(responseContent);
        System.out.println(responseContent.substring(17, responseContent.length() - 1));

        if (responseContent.startsWith("{\"failed\":")) {
            char errorCode = responseContent.charAt(10);
            //"failed":1 — история событий устарела или была частично утеряна, приложение может получать события далее, используя новое значение ts из ответа.
            //"failed":2 — истекло время действия ключа, нужно заново получить key методом messages.getLongPollServer.
            //"failed":3 — информация о пользователе утрачена, нужно запросить новые key и ts методом messages.getLongPollServer.
            switch (errorCode) {
                case '1' ->
                    ts = responseContent.trim().substring(17).concat("090909sad}{}{{/asddfe").replaceAll("[^0-9]", "");
                case '2' -> {
                    //serverData = vk.messages().getLongPollServer(actor).execute();
                    //key = serverData.getKey();
                }
                default -> {
                    //serverData = vk.messages().getLongPollServer(actor).execute();
                    //key = serverData.getKey();
                    //ts = String.valueOf(serverData.getTs());
                }
            }
        }

        System.out.println("ts=" + ts);
    }

    @Test
    public void testRemoveEmoji() throws FileNotFoundException, UnsupportedEncodingException {
        String emoji = "♡" + "Полина, 15, 📍Москва";

        String res = EmojiCleaner.clean(emoji);
        System.out.println(EmojiCleaner.isEmoji("♡"));
        System.out.println(EmojiCleaner.isEmoji("📍"));

        System.out.printf("before: %s\n after: %s\n", emoji, res);

        assertEquals("Полина, 15, Москва", res);

    }

    @Test
    public void testCreateSettingsJson() {
//        Map<Parameter, Object> settings = Settings.loadSettings();
//        
//        settings.put(Parameter.AGE_FILTER, false);
//        settings.put(Parameter.DEBUG_MODE, false);
//        settings.put(Parameter.HOURS_TO_SLEEP, 15);
//        settings.put(Parameter.SOUND_ALARM, false);
//        settings.put(Parameter.WINDOW_DIMENTIONS, new int[] {800, 700});
//        
//        Settings.saveSettings(settings);
    }

    @Test
    public void testSubstring() {
        System.out.println("vk.com/id386179098".substring(9));
    }

    @Test
    public void testExtractingDigits() {
        Pattern p = Pattern.compile("[\\p{Nd}]+");
        Matcher m = p.matcher("Ты понравилась 15 парням, показать их?");
        if (m.find()) {
            System.out.println(m.group());
        }
    }

}
