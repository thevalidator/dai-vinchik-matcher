/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javazoom.jl.player.Player;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Identifier {
                                       //(.+(<br>|\\n){1,})?(?<name>(.+)?,){0,1} (?<age>\\d{1,3},) (?<city>[a-zA-Zа-яА-я0-9,\\.-ёЁ ]+)(?<text>(((<br>)|\\n){1,}.+){0,})
    public static final String REGEXP = "(.+(<br>|\\n){1,})?(?<name>(.+)?,){0,1} (?<age>\\d{1,3},) (?<city>[a-zA-Zа-яА-я0-9,\\.\\-ёЁ ]+)(?<text>(((<br>)|\\n){1,}.+){0,})";
    //"(?<name>.+,) (?<age>\\d{1,3},) (?<city>[a-zA-Zа-яА-я0-9 \\-,\\.ёЁ]+)(?<text>(((<br>)|\\n){1,}.+){0,})";
//"(?<name>.+,) (?<age>\\d{1,3},) (?<city>[a-zA-Zа-яА-я0-9 -,.]+)(?<text>(((<br>)|\\n).+){0,})";
    
    public static boolean isProfile(String messageText, List<Button> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 4)) {
            if (!messageText.isEmpty() 
                    && messageText.matches(REGEXP)
                    //&& messageText.matches("^(?<name>.+,) (?<age>\\d{1,3},) (?<city>[a-zA-Zа-яА-я0-9 -]+)(?<text>(((<br>)|\\n).+){0,})")
                    //&& messageText.matches("(.+, \\d{1,3}, .+){1}((<br>|\\n).+)?")  //TODO: fix regexp
                    && "positive".equals(buttons.get(0).getColor())
                    && "positive".equals(buttons.get(1).getColor())
                    && "negative".equals(buttons.get(2).getColor())
                    && "default".equals(buttons.get(3).getColor())) {
                
                result = true;
            }
        }

        return result;
    }
    
    public static boolean isNoTextInProfileWarn(String messageText, List<Button> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 2)) {
            if (!messageText.isEmpty() 
                    && messageText.contains("В твоей анкете совсем нет текста, если ты напишешь "
                            + "немного о себе и кого ищешь, мы сможем лучше подобрать тебе пару.")
                    && "default".equals(buttons.get(0).getColor())
                    && "default".equals(buttons.get(1).getColor())) {
                
                result = true;
            }
        }

        return result;
    }
    
    public static boolean isExpired(String messageText, List<Button> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 2)) {
            if (!messageText.isEmpty() 
                    && messageText.endsWith("Ты можешь в один клик перейти к оценке анкет в Telegram и так же быстро вернуться в VK.")
                    && "positive".equals(buttons.get(0).getColor())
                    && "Анкеты в Telegram".equals(buttons.get(0).getAction().getLabel())
                    && "default".equals(buttons.get(1).getColor())
                    && "Анкеты в VK".equals(buttons.get(1).getAction().getLabel())) {
                
                result = true;
            }
        }

        return result;
    }
    
    public static boolean isLocation(String messageText, List<Button> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 1)) {
            if (!messageText.isEmpty() 
                    //&& messageText.contains("пришли мне свое местоположение и увидишь кто находится рядом")
                    && messageText.endsWith("пришли мне свое местоположение и увидишь кто находится рядом")) {
                    //&& "default".equals(buttons.get(0).getColor())
//                    && "Продолжить просмотр анкет".equals(buttons.get(0).getAction().getLabel())) {
                
                //startSoundAlarm();
                
                result = true;
            }
        }

        return result;
    }
    
    public static boolean isNeedSubscription(String messageText, List<Button> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 2)) {
            if (!messageText.isEmpty() 
                    && messageText.contains("Чтобы продолжить тебе необходимо подписаться на сообщество Дайвинчика")
                    && "positive".equals(buttons.get(0).getColor())
                    && "Продолжить".equals(buttons.get(0).getAction().getLabel())
                    && "default".equals(buttons.get(1).getColor())
                    && "Возможно позже".equals(buttons.get(1).getAction().getLabel())) {
                
                result = true;
            }
        }

        return result;
    }
    
    public static boolean isQuestion(String messageText, List<Button> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 2)) {
            if (!messageText.isEmpty() 
                    //&& messageText.contains("Подождем пока кто-то увидит твоб анкету")
                    && "positive".equals(buttons.get(0).getColor())
                    && "Как это работает?".equals(buttons.get(0).getAction().getLabel())
                    && "default".equals(buttons.get(1).getColor())
                    && "Хороший совет".equals(buttons.get(1).getAction().getLabel())) {
                
                result = true;
            }
        }

        return result;
    }
    
    public static boolean isTooManyLikes(String messageText, List<Button> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 4)) {
            if (!messageText.isEmpty() 
                    && messageText.contains("Слишком много лайков за сегодня")
                    && "positive".equals(buttons.get(0).getColor())
                    && "default".equals(buttons.get(1).getColor())
                    && "default".equals(buttons.get(2).getColor())
                    && "default".equals(buttons.get(3).getColor())) {
                
                result = true;
            }
        }

        return result;
    }
    
    public static boolean isSleeping(String messageText, List<Button> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 4)) {
            if (!messageText.isEmpty()     //1. Смотреть анкеты.<br>2. Моя анкета.<br>3. Я больше не хочу никого искать.<br>***<br>4. ✈️ Бот знакомств Дайвинчик в Telegram.
                    //&& messageText.endsWith("1. Смотреть анкеты.<br>2. Моя анкета.<br>3. Я больше не хочу никого искать.<br>***<br>4. ?? Бот знакомств Дайвинчик в Telegram.")
                    && messageText.startsWith("1. Смотреть анкеты.")
                    && messageText.endsWith("Бот знакомств Дайвинчик в Telegram.")
                    && "positive".equals(buttons.get(0).getColor())
                    && "default".equals(buttons.get(1).getColor())
                    && "default".equals(buttons.get(2).getColor())
                    && "default".equals(buttons.get(3).getColor())) {
                
                result = true;
            }
        }

        return result;
    }
    
    public static boolean isLikedBySomeone(String messageText, List<Button> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 2)) {
            if (!messageText.isEmpty() 
                    && messageText.contains("Ты понравил")
                    && messageText.contains("показать")
                    && "positive".equals(buttons.get(0).getColor())
                    && "default".equals(buttons.get(1).getColor())) {
                
                result = true;
            }
        }

        return result;
    }
    
    public static boolean isNewProfilesWantToMeet(String messageText, List<Button> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 2)) {
            if (!messageText.isEmpty() 
                    && messageText.contains("хотят познакомиться с тобой прямо сейчас")
                    && "Посмотреть".equals(buttons.get(0).getAction().getLabel())
                    && "Не интересно".equals(buttons.get(1).getAction().getLabel())) {
                
                result = true;
            }
        }

        return result;
    }
    
//    public static boolean isMutualLike(String messageText, List<Button> buttons) {
//        boolean result = false;
//        if ((buttons == null) || (buttons.isEmpty())) {
//            if (!messageText.isEmpty() 
//                    && messageText.startsWith("Есть взаимная симпатия! Добавляй в друзья")) {
//                
//                result = true;
//            }
//        }
//
//        return result;
//    }
    
    public static boolean isCaptcha(String messageText, List<Button> buttons) {
        boolean result = false;
        

        return result;
    }
    
    public static void startSoundAlarm() {
        String audioFilePath = "signal.mp3";
        try {
            BufferedInputStream buffer = new BufferedInputStream(
                    new FileInputStream(new File(audioFilePath))
            );
            Player mp3Player = new Player(buffer);
            mp3Player.play();
        } catch (Exception ex) {
            System.out.println("Error occured during playback process:" + ex.getMessage());
        }
    }

}
