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
import ru.thevalidator.daivinchikmatcher.property.Data;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class AlarmUtil {

    public static void startSoundAlarm() {
        String audioFilePath = Data.ALERT_PATH;
        try {
            BufferedInputStream buffer = new BufferedInputStream(
                    new FileInputStream(new File(audioFilePath))
            );
            Player mp3Player = new Player(buffer);
            mp3Player.play();
        } catch (FileNotFoundException | JavaLayerException ex) {
            System.out.println("Error occured during playback process:" + ex.getMessage());
            System.out.println(ExceptionUtil.getFormattedDescription(ex));
        }
    }

}
