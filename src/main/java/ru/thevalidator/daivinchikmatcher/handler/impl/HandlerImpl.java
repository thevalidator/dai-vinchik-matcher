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
import static ru.thevalidator.daivinchikmatcher.handler.Identifier.*;

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
//        System.out.println("message: " + messageText);
//        System.out.println("buttons:");
//        for (Button b : buttons) {
//            System.out.println("\tlabel=" + b.getAction().getLabel() + " payload:" + b.getAction().getPayload());
//        }
        
        if (buttons == null) {
            return null;
        }
        
        if (isProfile(messageText, buttons)) {
            //TODO: filter profiles
            System.out.println("[profile]");
            for (String s : dictionary) {
                if (messageText.contains(s)) {
                    return "1";
                }
            }
            return "3";
        } else if (isExpired(messageText, buttons)) {
            System.out.println("[expired]");
            return "2";
        } else if (isNeedSubscription(messageText, buttons)) {
            System.out.println("[subscription]");
            return "2";
        } else if (isNoTextInProfileWarn(messageText, buttons)) {
            System.out.println("no text my in profile warn");
            return "1";
        } else if (isLikedBySomeone(messageText, buttons)) {
            System.out.println("liked by someone");
            return "1";
        } else if (isSleeping(messageText, buttons)) {
            System.out.println("sleeping");
            return "1";
        } else if (isNewProfilesWantToMeet(messageText, buttons)) {
            System.out.println("new profiles");
            return "1";
        } else if (isQuestion(messageText, buttons)) {
            System.out.println("question");
            return "2";
        } else if (isLocation(messageText, buttons)) {
            System.out.println("location");
            return "2";
        } else {
            throw new UnsupportedOperationException("Unknown state");
        }

        //return "1";
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
