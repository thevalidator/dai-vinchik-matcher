/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.matcher.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.thevalidator.daivinchikmatcher.matcher.Matcher;

public class MatcherImpl implements Matcher {

    private final Set<String> dictionary;

    public MatcherImpl() {
        this.dictionary = readDict("match.dict");
    }

    @Override
    public boolean hasMatched() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static Set<String> readDict(String path) {
        Set<String> dict = new HashSet<>();
        try ( BufferedReader br
                = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                dict.add(line.trim());
            }
        } catch (IOException ex) {
            Logger.getLogger(MatcherImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dict;
    }

}
