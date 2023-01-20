/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javazoom.jl.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.thevalidator.daivinchikmatcher.property.Data;
import ru.thevalidator.daivinchikmatcher.util.EmojiCleaner;

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
    public void testPlayMusic() {
        String audioFilePath = Data.ALERT_PATH;
        //SoundPlayerUsingJavaZoom player = new SoundPlayerUsingJavaZoom();
        try {
            BufferedInputStream buffer = new BufferedInputStream(
                    //player.getClass().getClassLoader().getResourceAsStream(audioFilePath)
                    new FileInputStream(new File(audioFilePath))
            );
            Player mp3Player = new Player(buffer);
            mp3Player.play();
        } catch (Exception ex) {
            System.out.println("Error occured during playback process:" + ex.getMessage());
        }
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
    public void testFind101Symbol() {
        String s = "https://im.vk.com/nim527463828?act=a_check&key=b8b86a057a76b04c8d135b7c2d14870dfdc06473&ts=1754547044";
        //System.out.println(s.length());
        System.out.println(s.charAt(100));
    }

    @Test
    public void testContainsWord() {

        System.out.println(("Amwfck, 23, Сарапул\nПообщаться").contains("бщат"));
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

//    @Test
//    public void testRemoveEmoji() {
//        
//        String textWithoutEmoji = "test";
//        String emojiText = textWithoutEmoji + "♡";
//        String result = EmojiParser.removeAllEmojis(emojiText);
//        
//        for (char c : emojiText.toCharArray()) {
//            if (c >= 8986 && c <= 129510) {
//                System.out.println("emoji: " + c);
//            }
//        }
//        //8986
//        //129510
//        System.out.printf("before: %s\n after: %s\n", emojiText, result);
//        
//        assertEquals(textWithoutEmoji, result);
//    } 
    @Test
    public void testRemoveEmoji2() {

        String textWithoutEmoji = "test";
        String emojiText = textWithoutEmoji + "♡";
        String result = EmojiCleaner.clean(emojiText);

//        for (char c : emojiText.toCharArray()) {
//            if (c >= 8986 && c <= 129510) {
//                System.out.println("emoji: " + c);
//            }
//        }
        System.out.printf("before: %s\n after: %s\n", emojiText, result);

        assertEquals(textWithoutEmoji, result);
    }

    @Test
    public void testRemoveEmoji3() throws FileNotFoundException, UnsupportedEncodingException {
        String emoji = "♡" + "Полина, 15, 📍Москва";
        
        String res = EmojiCleaner.clean(emoji);
        System.out.println(EmojiCleaner.isEmoji("♡"));
        System.out.println(EmojiCleaner.isEmoji("📍"));
        
        System.out.printf("before: %s\n after: %s\n", emoji, res);
        
        assertEquals("Полина, 15, Москва", res);

////        for (char c : emoji.toCharArray()) {
////            if (c == 128205) {
////                System.out.println("EMOJIII");
////            }
////            System.out.println(c + " : " + Integer.valueOf(c));
////        }
////
////        for (int i = 0; i < emoji.length(); i++) {
////            System.out.println(">> " + emoji.charAt(i));
////        }
////
////        //emoji = EmojiParser.removeAllEmojis(emoji);
////        //? : 55357 ? : 56525
////        //55356/57088-55357/56911    /55357/56960-55357/57087
////        //emoji = emoji.replaceAll("[\ud83c\udf00-\ud83d\ude4f]|[\ud83d\ude80-\ud83d\udeff]", "");
////        emoji = emoji.replaceAll("[\u231A-\ud83d\udeff]", "");
////
////        System.out.println(emoji);
////
////        
////        try ( PrintWriter out = new PrintWriter("emoji.txt", "UTF-8")) {
////
////            for (int i = 8_986; i <= 129_510; i++) {
////                String symbol = Character.toString(i);
////                String code = "";
////                for (char c : symbol.toCharArray()) {
////                    code = code + Integer.valueOf(c) + "/";
////                }
////                String s = "1) " + symbol + " - 2) " + i + " - 3) " + code;
////                out.println(s);
////                //sb.append(Character.toString(i));
////            }
////
////            
////        }
////        StringBuilder sb = new StringBuilder();
////        sb.append("a");
////        sb.append("b");
////        String res = sb.toString();
////
////        //res=res.replaceAll("[\u231A-\ud83d\udeff]", "");
////        //res = res.replaceAll("[\ud83c\udf00-\ud83d\ude4f]|[\ud83d\ude80-\ud83d\udeff]", "");
////        //res = EmojiParser.removeAllEmojis(res);
////        res = EmojiCleaner.clean(res);
////
////        assertEquals("ab", res);

        //System.out.println(Character.getNumericValue(emoji.charAt(0)));
    }

}
