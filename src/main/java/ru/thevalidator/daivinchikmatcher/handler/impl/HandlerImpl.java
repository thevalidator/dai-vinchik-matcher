/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler.impl;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import ru.thevalidator.daivinchikmatcher.util.Reader;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;
import ru.thevalidator.daivinchikmatcher.handler.Handler;
import static ru.thevalidator.daivinchikmatcher.handler.Identifier.*;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import ru.thevalidator.daivinchikmatcher.matcher.impl.FilterImpl;

public class HandlerImpl implements Handler {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(HandlerImpl.class);
    private final Set<String> continueWords;
    private final Filter filter;

    public HandlerImpl() {
        this.continueWords = Reader.readDict("continue.dict");
        this.filter = new FilterImpl();
    }

    @Override
    public String getAnswer(String messageText, List<Button> buttons) {
        
        if (buttons == null) { //&& !messageText.contains("Нет такого варианта ответа, напиши одну цифру")) {
            return null;
        }
        
        //checks continue words on buttons
        if (!buttons.isEmpty()) {
            for (Button b : buttons) {
                String buttonText = b.getAction().getLabel();
                if (buttonText.length() > 1) {
                    for (int i = 0; i < buttonText.length(); i++) {
                        if (Character.isLetter(buttonText.charAt(i))) {
                            if (continueWords.contains(buttonText)) {
                                return b.getAction().getPayload();
                            }
                        }
                    }
                }
            }
        }
        
        if (isProfile(messageText, buttons)) {
            System.out.println("[profile]");
            if (filter.isMatched(messageText)) {
                return "1";
            } else {
                return "3";
            }
            
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
            logger.error("unknown state");
            System.out.println("[ERROR] - message:" + messageText + "\nbuttons: " + buttons.size() + "\t" + buttons.get(0).getColor());
            System.out.print("\nENTER CORRECT ANSWER:");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            System.out.println("");
            
            return "[CASE]-" + answer;
            //throw new UnsupportedOperationException("Unknown state");
        }
        
    }

}
