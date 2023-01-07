/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javazoom.jl.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        String audioFilePath = "signal.mp3";
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

}
