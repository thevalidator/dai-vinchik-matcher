/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.v2.identity;

import com.vk.api.sdk.objects.messages.KeyboardButton;
import com.vk.api.sdk.objects.messages.KeyboardButtonColor;
import java.util.List;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class CaseMatcher {

    public static final String PROFILE_REGEXP = "([\\p{L}\\p{N}\\p{P}\\p{Z}\\W$\\^+=|`~№]+(<br>|\\n){1,})?(?<name>([\\p{L}\\p{N}\\p{P}\\p{Z}\\W$\\^+=|`~№]+)?,){1} (?<age>\\d{1,3},){1} (?<city>[\\p{L}\\p{N}\\p{P}\\p{Z}$\\^+=|`~№]+){1}(?<text>(((<br>)|\\n){1,}.{0,}){0,})";
    public static final String BUTTON_REGEXP = "[\\p{L}\\p{N}\\p{P}\\p{Z}]{2,}";

    public static boolean isProfile(String messageText, List<KeyboardButton> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 4)) {
            if (!messageText.isEmpty()
                    && messageText.matches(PROFILE_REGEXP)
                    && KeyboardButtonColor.POSITIVE.equals(buttons.get(0).getColor())
                    && KeyboardButtonColor.DEFAULT.equals(buttons.get(3).getColor())) {

                result = true;
            }
        }

        return result;
    }

    public static boolean isNoTextInProfileWarn(String messageText, List<KeyboardButton> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 2)) {
            if (!messageText.isEmpty()
                    && messageText.contains("В твоей анкете совсем нет текста, если ты напишешь "
                            + "немного о себе и кого ищешь, мы сможем лучше подобрать тебе пару.")
                    && KeyboardButtonColor.DEFAULT.equals(buttons.get(0).getColor())
                    && KeyboardButtonColor.DEFAULT.equals(buttons.get(1).getColor())) {

                result = true;
            }
        }

        return result;
    }

    public static boolean isAdvertisement(String messageText, List<KeyboardButton> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 2)) {
            if (!messageText.isEmpty()
                    && buttons.get(0).getAction().getLabel().matches("[\\p{L}\\p{N}\\p{P}\\p{Z}]+")
                    && KeyboardButtonColor.POSITIVE.equals(buttons.get(0).getColor())
                    && buttons.get(1).getAction().getLabel().matches("[\\p{L}\\p{N}\\p{P}\\p{Z}]+")
                    && KeyboardButtonColor.DEFAULT.equals(buttons.get(1).getColor())) {

                result = true;
            }
        }

        return result;
    }

    public static boolean isLocation(String messageText, List<KeyboardButton> buttons) {
        boolean result = false;
        if (messageText.endsWith("пришли мне свое местоположение и увидишь кто находится рядом")) {
            result = true;
        }

        return result;
    }

    public static boolean isTooManyLikes(String messageText, List<KeyboardButton> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 4)) {
            if (!messageText.isEmpty()
                    && messageText.contains("Слишком много лайков за сегодня")
                    && KeyboardButtonColor.POSITIVE.equals(buttons.get(0).getColor())
                    && KeyboardButtonColor.DEFAULT.equals(buttons.get(1).getColor())
                    && KeyboardButtonColor.DEFAULT.equals(buttons.get(2).getColor())
                    && KeyboardButtonColor.DEFAULT.equals(buttons.get(3).getColor())) {

                result = true;
            }
        }

        return result;
    }

    public static boolean isSleeping(String messageText, List<KeyboardButton> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 4)) {
            if (!messageText.isEmpty()
                    && messageText.startsWith("1. Смотреть анкеты.")
                    && messageText.endsWith("Бот знакомств Дайвинчик в Telegram.")
                    && KeyboardButtonColor.POSITIVE.equals(buttons.get(0).getColor())
                    && KeyboardButtonColor.DEFAULT.equals(buttons.get(1).getColor())
                    && KeyboardButtonColor.DEFAULT.equals(buttons.get(2).getColor())
                    && KeyboardButtonColor.DEFAULT.equals(buttons.get(3).getColor())) {

                result = true;
            }
        }

        return result;
    }

    public static boolean isLikedBySomeone(String messageText, List<KeyboardButton> buttons) {
        boolean result = false;
        if ((buttons != null) && (buttons.size() == 2)) {
            if (!messageText.isEmpty()
                    && messageText.contains("Ты понравил")
                    && messageText.contains("показать")
                    && KeyboardButtonColor.POSITIVE.equals(buttons.get(0).getColor())
                    && KeyboardButtonColor.DEFAULT.equals(buttons.get(1).getColor())) {

                result = true;
            }
        }

        return result;
    }

    public static boolean isLikedBySomeoneAfterProfile(String messageText, List<KeyboardButton> buttons) {
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

    public static boolean isNewProfilesWantToMeet(String messageText, List<KeyboardButton> buttons) {
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

    public static boolean isMutualLike(String messageText, List<KeyboardButton> buttons) {
        boolean result = false;
        if (!messageText.isEmpty()
                && messageText.contains("в друзья - vk.com")) {

            result = true;
        }

        return result;
    }

    public static boolean isAdvise(String messageText, List<KeyboardButton> buttons) {
        boolean result = false;
        if (!messageText.isEmpty()
                && messageText.contains("Твоя анкета может собирать больше лайков")) {

            result = true;
        }

        return result;
    }

    public static boolean isCaptcha(String messageText, List<KeyboardButton> buttons) {
        boolean result = false;

        return result;
    }

}
