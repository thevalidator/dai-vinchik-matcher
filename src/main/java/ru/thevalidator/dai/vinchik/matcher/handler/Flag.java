/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.dai.vinchik.matcher.handler;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public enum Flag {
    
//         +1 UNREAD - Сообщение не прочитано
//         +2 OUTBOX - Исходящее сообщение
//         +4 REPLIED - На сообщение был создан ответ)
//         +8 IMPORTANT - Помеченное сообщение
//        +16 CHAT - Сообщение отправлено через чат. Обратите внимание, этот флаг устаревший и вскоре перестанет поддерживаться.
//        +32 FRIENDS - Сообщение отправлено другом. Не применяется для сообщений из групповых бесед.
//        +64 SPAM - Сообщение помечено как спам.
//       +128 DELЕTЕD - Сообщение удалено (в корзине)
//       +256 FIXED - Сообщение проверено пользователем на спам. Обратите внимание, этот флаг устаревший и вскоре перестанет поддерживаться.
//       +512 MEDIA - Сообщение содержит медиаконтент. Обратите внимание, этот флаг устаревший и вскоре перестанет поддерживаться.
//     +65536 HIDDEN - Приветственное сообщение от сообщества. Диалог с таким сообщением не нужно поднимать в списке (отображать его только при открытии диалога напрямую). Флаг недоступен для версий <2.
//    +131072 DELETE_FOR_ALL - Сообщение удалено для всех получателей. Флаг недоступен для версий <3.
//    +262144 NOT_DELIVERED
    
    UNREAD (1),
    OUTBOX (2),
    REPLIED (4),
    IMPORTANT (8),
    CHAT (16),
    FRIENDS (32),
    SPAM (64),
    DELЕTЕD (128),
    FIXED (256),
    MEDIA (512),
    HIDDEN (65536),
    DELETE_FOR_ALL (131072),
    NOT_DELIVERED (262144);
    
    private final int flagCode;

    private Flag(int flagCode) {
        this.flagCode = flagCode;
    }

    public int getFlagCode() {
        return flagCode;
    }
    
    public static int[] getAllFlagCodes() {
        int[] codes = new int[Flag.values().length];
        int i = 0;
        for (Flag f: Flag.values()) {
            codes[i++] = f.getFlagCode();
        }
        
        return codes;
    }
    
}
