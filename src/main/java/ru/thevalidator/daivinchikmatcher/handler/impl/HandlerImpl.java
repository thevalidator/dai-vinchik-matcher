/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler.impl;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.daivinchikmatcher.util.FileUtil;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;
import ru.thevalidator.daivinchikmatcher.handler.Handler;
import static ru.thevalidator.daivinchikmatcher.handler.Identifier.*;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import ru.thevalidator.daivinchikmatcher.matcher.impl.FilterImpl;

public class HandlerImpl implements Handler {

    private static final Logger logger = LogManager.getLogger(HandlerImpl.class);
    private final Set<String> continueWords;
    private final Filter filter;

    public HandlerImpl() {
        this.continueWords = FileUtil.readDict("continue.dict");
        this.filter = new FilterImpl();
    }

    @Override
    public String getAnswer(String messageText, List<Button> buttons) {

        if (buttons == null) { //&& !messageText.contains("Нет такого варианта ответа, напиши одну цифру")) {
            if (messageText.startsWith("Есть взаимная симпатия! Добавляй в друзья -")) {
                return null;
            } else if (messageText.startsWith("Нет такого варианта ответа")) {
                System.out.print("ENTER CORRECT ANSWER:");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.nextLine();
                //System.out.println("");

                return "[CASE]-" + answer;
            } else {
                logger.error("[NO KEYBOARD MSG] - {}", messageText);
                System.out.print("MESSAGE WITHOUT KEYBOARD, SKIPPING, CHECK IF BOT WORKS!!!");
                return null;
            }

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
            //logger.info("[PROFILE] - {}", messageText);
            System.out.println("[PROFILE] - " + messageText);
            if (filter.isMatched(messageText)) {
                logger.info("[MATCH]");
                return "1";
            } else {
                logger.info("[NO MATCH]");
                return "3";
            }

        } else if (isExpired(messageText, buttons)) {
            System.out.println("[EXPIRED]");
            return "2";
        } else if (isNeedSubscription(messageText, buttons)) {
            System.out.println("[SUBSCRIPTION]");
            return "2";
        } else if (isNoTextInProfileWarn(messageText, buttons)) {
            System.out.println("[NO TEXT MY IN PROFILE WARN]");
            return "1";
        } else if (isLikedBySomeone(messageText, buttons)) {
            System.out.println("[LIKED BY SOMEONE]");
            return "1";
        } else if (isSleeping(messageText, buttons)) {
            System.out.println("[SLEEPING]");
            return "1";
        } else if (isNewProfilesWantToMeet(messageText, buttons)) {
            System.out.println("[NEW PROFILES]");
            return "1";
        } else if (isQuestion(messageText, buttons)) {
            System.out.println("[QUESTION]");
            return "2";
        } else if (isLocation(messageText, buttons)) {
            System.out.println("[LOCATION]");
            return "2";
        } else {
            logger.error("unknown state, need assistance");
            //System.out.println("[ERROR] - message:" + messageText + "\nbuttons: " + buttons.size() + "\t" + buttons.get(0).getColor());
            System.out.print("ENTER CORRECT ANSWER:");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            //System.out.println("");

            return "[CASE]-" + answer;
            //throw new UnsupportedOperationException("Unknown state");
        }

    }

}
