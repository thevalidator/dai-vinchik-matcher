/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.thevalidator.daivinchikmatcher.handler.impl.HandlerImpl;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class FileUtil {

    public static Set<String> readDict(String path) {
        Set<String> dict = new HashSet<>();
        try ( BufferedReader br
                = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                dict.add(line.trim());
            }
        } catch (IOException ex) {
            Logger.getLogger(HandlerImpl.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(HandlerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ages;
    }

    public static String readDelay(String path) {
        try ( BufferedReader br
                = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String line = br.readLine().trim();
            if (line != null && line.matches("\\d{1,2}")) {
                //max 90
                int delay = Integer.parseInt(line);
                if (delay > 2 && delay <= 90) {
                    return line.trim();
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(HandlerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        throw new IllegalArgumentException("Wrong data in file " + path);
    }

}
