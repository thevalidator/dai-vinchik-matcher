/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class SoundUtil {
    
    private static final Logger logger = LogManager.getLogger(SoundUtil.class);

    public static void startSoundAlarm() {
        String audioFilePath = "media/signal.mp3";
        try {
            BufferedInputStream buffer = new BufferedInputStream(
                    new FileInputStream(new File(audioFilePath))
            );
            Player mp3Player = new Player(buffer);
            mp3Player.play();
        } catch (FileNotFoundException | JavaLayerException ex) {
            //System.out.println("Error occured during playback process:" + ex.getMessage());
            logger.error(ExceptionUtil.getFormattedDescription(ex));
        }
    }
    
    public static void startSounNotification() {
        String audioFilePath = "media/notification.mp3";
        try {
            BufferedInputStream buffer = new BufferedInputStream(
                    new FileInputStream(new File(audioFilePath))
            );
            Player mp3Player = new Player(buffer);
            mp3Player.play();
        } catch (FileNotFoundException | JavaLayerException ex) {
            //System.out.println("Error occured during playback process:" + ex.getMessage());
            logger.error(ExceptionUtil.getFormattedDescription(ex));
        }
    }

}
