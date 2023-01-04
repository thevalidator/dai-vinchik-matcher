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

}
