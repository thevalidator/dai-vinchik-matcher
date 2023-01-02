/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;
import ru.thevalidator.daivinchikmatcher.handler.Handler;

public class HandlerImpl implements Handler {

    private final Set<String> dictionary;

    public HandlerImpl() {
        this.dictionary = readDict("match.dict");
    }

    public boolean hasMatched(String text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void handleAnswer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAnswer(String messageText, List<Button> buttons) {
        System.out.println("message: " + messageText);
        System.out.println("buttons:");
        for (Button b : buttons) {
            System.out.println("\tlabel=" + b.getAction().getLabel() + " payload:" + b.getAction().getPayload());
        }
        return "1";
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
            Logger.getLogger(HandlerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dict;
    }

}
