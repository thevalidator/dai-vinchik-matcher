/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static ru.thevalidator.daivinchikmatcher.property.Data.ALERT_PATH;
import static ru.thevalidator.daivinchikmatcher.property.Data.NOTIFICATION_PATH;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class SoundUtil {

    private static final Logger logger = LogManager.getLogger(SoundUtil.class);

    public static void playAlarm() {
        playSound(ALERT_PATH);
    }

    public static void playNotification() {
        playSound(NOTIFICATION_PATH);
    }

    private static void playSound(String audioFilePath) {
        try (FileInputStream fis = new FileInputStream(new File(audioFilePath));
                BufferedInputStream buffer = new BufferedInputStream(fis)) {
            
            Player mp3Player = new Player(buffer);
            mp3Player.play();
            
        } catch (IOException | JavaLayerException ex) {
            logger.error(ExceptionUtil.getFormattedDescription(ex));
        }
    }

}
