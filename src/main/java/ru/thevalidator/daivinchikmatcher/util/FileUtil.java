/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.apache.logging.log4j.LogManager;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class FileUtil {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(FileUtil.class);
    
    public static Set<String> readDict(String path) {
        Set<String> dict = new HashSet<>();
        try ( BufferedReader br
                = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                dict.add(line.trim());
            }
        } catch (IOException ex) {
            logger.error(ExceptionUtil.getFormattedDescription(ex));
        }

        return dict;
    }

    public static int[] readAgeDict(String path) {
        int[] ages = new int[2];
        try ( BufferedReader br
                = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String line = br.readLine().trim();
            if (line != null && line.matches("\\d{1,3}-\\d{1,3}")) {
                ages[0] = Integer.parseInt(line.substring(0, line.indexOf("-")));
                ages[1] = Integer.parseInt(line.substring(line.indexOf("-") + 1));
            }

        } catch (IOException ex) {
            logger.error(ExceptionUtil.getFormattedDescription(ex));
        }

        return ages;
    }
    
    public static String readMessageDict(String path) {
        StringBuilder sb = new StringBuilder();
        try ( BufferedReader br
                = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException ex) {
            logger.error(ExceptionUtil.getFormattedDescription(ex));
        }

        return sb.toString();
    }
    
    public static LinkedList<String> readMessagesDict(String path) {
        LinkedList<String> messages = new LinkedList<>();
        
        StringBuilder sb = new StringBuilder();
        try ( BufferedReader br
                = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(">>>")) {
                    sb.append(line).append("\n");
                } else {
                    messages.add(sb.toString());
                    sb.setLength(0);
                }
            }
            if (!sb.isEmpty()) {
                messages.add(sb.toString());
            }
        } catch (IOException ex) {
            logger.error(ExceptionUtil.getFormattedDescription(ex));
        }

        return messages;
    }

}
