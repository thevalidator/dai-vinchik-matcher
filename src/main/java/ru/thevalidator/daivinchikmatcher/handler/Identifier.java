/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.handler;

import com.vdurmont.emoji.EmojiParser;
import java.util.List;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Identifier {             
    
                                      //  \p{L} – to allow all letters from any language
                                      //  \p{N} – for numbers
                                      //  \p{P} – for punctuation
                                      //  \p{Z} – for whitespace separators
    
                                      //  \\p{L}\\p{N}\\p{P}\\p{Z}
                                      //"([\\p{L}\\p{N}\\p{P}\\p{Z}$^+=|><`~№]+(<br>|\\n){1,})?(?<name>([\\p{L}\\p{N}\\p{P}\\p{Z}$^+=|><`~№]+),){1} (?<age>\\d{1,3},){1} (?<city>[\\p{L}\\p{N}\\p{P}\\p{Z}$^+=|><`~№]+){1}(?<text>(((<br>)|\\n){1,}.{0,}){0,})";
                                      //"(.+(<br>|\\n){1,})?(?<name>(.+)?,){1} (?<age>\\d{1,3},){1} (?<city>[a-zA-Zа-яА-я0-9,\\.\\-–ёЁ()? ]+){1}(?<text>(((<br>)|\\n){1,}.{0,}){0,})";
                                      //"(.+(<br>|\\n){1,})?(?<name>(.+)?,){0,1} (?<age>\\d{1,3},) (?<city>[a-zA-Zа-яА-я0-9,\\.\\-–ёЁ()? ]+)(?<text>(((<br>)|\\n){1,}.{0,}){0,})";
    public static final String REGEXP = "([\\p{L}\\p{N}\\p{P}\\p{Z}\\W$\\^+=|`~№]+(<br>|\\n){1,})?(?<name>([\\p{L}\\p{N}\\p{P}\\p{Z}\\W$\\^+=|`~№]+)?,){1} (?<age>\\d{1,3},){1} (?<city>[\\p{L}\\p{N}\\p{P}\\p{Z}$\\^+=|`~№]+){1}(?<text>(((<br>)|\\n){1,}.{0,}){0,})";
    public static final String BUTTON_REGEXP = "[\\p{L}\\p{N}\\p{P}\\p{Z}]{2,}";
    
    public static boolean isProfile(String messageText, List<Button> buttons) {
        String text = EmojiParser.removeAllEmojis(messageText);
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 4)) {
            if (!text.isEmpty() 
                    && text.matches(REGEXP)
                    && "positive".equals(buttons.get(0).getColor())
                    //&& "positive".equals(buttons.get(1).getColor())
                    //&& "negative".equals(buttons.get(2).getColor())
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
    
    public static boolean isAdvertisement(String messageText, List<Button> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 2)) {
            if (!messageText.isEmpty() 
                    //&& messageText.endsWith("Ты можешь в один клик перейти к оценке анкет в Telegram и так же быстро вернуться в VK.")
                    && buttons.get(0).getAction().getLabel().matches("[\\p{L}\\p{N}\\p{P}\\p{Z}]+")
                    && "positive".equals(buttons.get(0).getColor())
                    //&& "Анкеты в Telegram".equals(buttons.get(0).getAction().getLabel())
                    && buttons.get(1).getAction().getLabel().matches("[\\p{L}\\p{N}\\p{P}\\p{Z}]+")
                    && "default".equals(buttons.get(1).getColor())) {
                    //&& "Анкеты в VK".equals(buttons.get(1).getAction().getLabel())) {
                
                result = true;
            }
        }

        return result;
    }
    
    public static boolean isLocation(String messageText, List<Button> buttons) {
        boolean result = false;
        //if ((buttons != null) && (buttons.size() == 1)) {
            if (!messageText.isEmpty() 
                    && messageText.endsWith("пришли мне свое местоположение и увидишь кто находится рядом")) {
                //System.out.println("LOCATION");
                result = true;
            }
        //}

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
            if (!messageText.isEmpty()    
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
    
    public static boolean isLikedBySomeoneAfterProfile(String messageText, List<Button> buttons) {
        boolean result = false;
        if ((buttons == null)) {
            //Кому-то понравилась твоя анкета! Заканчивай с вопросом выше и посмотрим кто это
            if (!messageText.isEmpty() 
                    //&& messageText.contains("Кому-то понравилась твоя анкета")
                    && messageText.contains("Кому-то понравилась твоя анкета")) {
                
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
    
    public static boolean isMutualLike(String messageText, List<Button> buttons) {
        boolean result = false;
        //if ((buttons == null) || (buttons.isEmpty())) {
            if (!messageText.isEmpty() 
                    && messageText.startsWith("Есть взаимная симпатия! Добавляй в друзья")) {
                
                result = true;
            }
        //}

        return result;
    }
    
    public static boolean isAdvise(String messageText, List<Button> buttons) {
        boolean result = false;
            if (!messageText.isEmpty() 
                    && messageText.contains("Твоя анкета может собирать больше лайков")) {
                
                result = true;
            }

        return result;
    }
    
    public static boolean isCaptcha(String messageText, List<Button> buttons) {
        boolean result = false;
        

        return result;
    }

}
