/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.settings;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.daivinchikmatcher.dto.settings.SettingsDTO;
import ru.thevalidator.daivinchikmatcher.exception.CannotLoadSettingException;
import ru.thevalidator.daivinchikmatcher.util.ExceptionUtil;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Settings {

    private static final Logger logger = LogManager.getLogger(Settings.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String SETTINGS_PATH = "config/settings.json";

    private static void createDefaultSettingsFile() {

        SettingsDTO dto = new SettingsDTO();

        dto.setAgeFilter(true);
        dto.setCityFilter(true);
        dto.setTextFilter(true);

        dto.setDebugMode(true);
        dto.setSoundAlarm(true);
        dto.setExperimentalHandler(true);

        dto.setHoursToSleep(12);
        dto.setLongPollDelay("25");
        dto.setLikeOnLike(true);
        dto.setWindowDimentions(new int[]{600, 500});
        
        dto.setBaseDelay(10);
        dto.setRandomDelay(15);
        dto.setReplyCheckPeriod(300);

        try {
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(Paths.get(SETTINGS_PATH).toFile(), dto);
        } catch (IOException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
        }
    }

    public static Map<Parameter, Object> loadSettings() {

        File configFile = Paths.get(SETTINGS_PATH).toFile();

        if (!configFile.exists()) {
            createDefaultSettingsFile();
        }

        try {
            Map<Parameter, Object> map = new ConcurrentHashMap<>();
            SettingsDTO dto = mapper.readValue(configFile, SettingsDTO.class);

            map.put(Parameter.AGE_FILTER, dto.hasAgeFilter());
            map.put(Parameter.CITY_FILTER, dto.hasCityFilter());
            map.put(Parameter.TEXT_FILTER, dto.hasTextFilter());
            map.put(Parameter.DEBUG_MODE, dto.isDebugMode());
            map.put(Parameter.EXPERIMENTAL_HANDLER, dto.hasExperimentalHandler());
            map.put(Parameter.HOURS_TO_SLEEP, dto.getHoursToSleep());
            map.put(Parameter.LONG_POLL_DELAY, dto.getLongPollDelay());
            map.put(Parameter.SOUND_ALARM, dto.hasSoundAlarm());
            map.put(Parameter.WINDOW_DIMENTIONS, dto.getWindowDimentions());
            map.put(Parameter.LIKE_ON_LIKE, dto.shouldLikeOnLike());
            map.put(Parameter.BASE_DELAY, dto.getBaseDelay());
            map.put(Parameter.RANDOM_DELAY, dto.getRandomDelay());
            map.put(Parameter.REPLY_CHECK_PERIOD, dto.getReplyCheckPeriod());

            return map;

        } catch (IOException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
        }

        throw new CannotLoadSettingException();

    }

    public static void saveSettings(Map<Parameter, Object> settings) {

        SettingsDTO dto = new SettingsDTO();
        
        dto.setAgeFilter((boolean) settings.get(Parameter.AGE_FILTER));
        dto.setCityFilter((boolean) settings.get(Parameter.CITY_FILTER));
        dto.setTextFilter((boolean) settings.get(Parameter.TEXT_FILTER));
        dto.setDebugMode((boolean) settings.get(Parameter.DEBUG_MODE));
        dto.setExperimentalHandler((boolean) settings.get(Parameter.EXPERIMENTAL_HANDLER));
        dto.setSoundAlarm((boolean) settings.get(Parameter.SOUND_ALARM));
        dto.setLikeOnLike((boolean) settings.get(Parameter.LIKE_ON_LIKE));
        dto.setHoursToSleep((int) settings.get(Parameter.HOURS_TO_SLEEP));
        dto.setLongPollDelay((String) settings.get(Parameter.LONG_POLL_DELAY));
        dto.setWindowDimentions((int[]) settings.get(Parameter.WINDOW_DIMENTIONS));
        dto.setBaseDelay((int) settings.get(Parameter.BASE_DELAY));
        dto.setRandomDelay((int) settings.get(Parameter.RANDOM_DELAY));
        dto.setReplyCheckPeriod((int) settings.get(Parameter.REPLY_CHECK_PERIOD));
        
        try {
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(Paths.get(SETTINGS_PATH).toFile(), dto);
        } catch (IOException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
        }

    }

    public static void printSettings(Map<Parameter, Object> settings) {
        for (Map.Entry<Parameter, Object> entry : settings.entrySet()) {
            Parameter key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("p: " + key + " v: " + String.valueOf(value));
        }
    }

}
