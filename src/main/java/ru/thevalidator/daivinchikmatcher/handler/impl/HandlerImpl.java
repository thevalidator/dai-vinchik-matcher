/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler.impl;

import ru.thevalidator.daivinchikmatcher.exception.TooManyLikesException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.GetHistoryRev;
import com.vk.api.sdk.objects.messages.KeyboardButton;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.daivinchikmatcher.util.FileUtil;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Button;
import ru.thevalidator.daivinchikmatcher.dto.keyboard.Keyboard;
import ru.thevalidator.daivinchikmatcher.handler.Code;
import ru.thevalidator.daivinchikmatcher.handler.Flag;
import ru.thevalidator.daivinchikmatcher.handler.Handler;
import static ru.thevalidator.daivinchikmatcher.handler.Identifier.*;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import ru.thevalidator.daivinchikmatcher.matcher.impl.ProfileMatchCheckerImpl;
import ru.thevalidator.daivinchikmatcher.matcher.MatchChecker;
import ru.thevalidator.daivinchikmatcher.parser.ResponseParser;
import static ru.thevalidator.daivinchikmatcher.property.Data.DAI_VINCHIK_BOT_CHAT_ID;
import ru.thevalidator.daivinchikmatcher.util.ExceptionUtil;
import ru.thevalidator.daivinchikmatcher.util.VKUtil;

public class HandlerImpl implements Handler {

    private static final Logger logger = LogManager.getLogger(HandlerImpl.class);
    private static final int[] flags = Flag.getAllFlagCodes();
    private final Set<String> continueWords;
    private final MatchChecker checker;
    private VkApiClient vk;
    private UserActor actor;

    public HandlerImpl(Set<Filter> filters, VkApiClient vk, UserActor actor) {
        this.continueWords = FileUtil.readDict("continue.dict");
        this.checker = new ProfileMatchCheckerImpl(filters);
        this.vk = vk;
        this.actor = actor;
    }

    @Override
    public String getStartMessage(String lastMessageText, List<Button> buttons) {
        return generateMessage(lastMessageText, buttons, null);
    }

    @Override
    public String getAnswerMessage(List<List<Object>> updates) {
        String answer = null;
        for (int i = updates.size() - 1; i >= 0; i--) {
        //for (List<Object> o : updates) {
            Integer code = (Integer) updates.get(i).get(0);

            if (code == Code.INCOMING_MESSAGE) {
                Integer flagsSum = (Integer) updates.get(i).get(2);
                Set<Integer> messageFlags = getFlags(flagsSum);

                if (!messageFlags.contains(Flag.OUTBOX.getFlagCode()) && updates.get(i).size() == 8) {
                    Integer minorId = (Integer) updates.get(i).get(3);

                    if (minorId == DAI_VINCHIK_BOT_CHAT_ID) { //2_000_000_000  // > 
                        //System.out.println("[INCOMING DAIVIN] - " + o.toString());                      //debug. delete in future
                        String message = updates.get(i).get(5).toString();
                        Keyboard actualKeyboard = ResponseParser.parseKeyboard(updates.get(i).get(6));
                        List<Button> buttons = actualKeyboard != null
                                ? actualKeyboard.getButtons().get(actualKeyboard.getButtons().size() - 1)
                                : null;

                        if (buttons == null) {
                            if (message.startsWith("Есть взаимная симпатия! Добавляй в друзья -")) {
                                System.out.println("\"[MUTUAL LIKE CASE] - " +  message);
                                logger.info("[MUTUAL LIKE CASE] - {}", message);
                            } else if (message.startsWith("Нет такого варианта ответа")) {
                                answer = getCustomAnswer(updates);
                                break;
                            }
                        } else {
                            answer = generateMessage(message, buttons, updates);
                            break;
                        }
                    }

                }

            }
        }

        return answer;
    }

    private String generateMessage(String messageText, List<Button> buttons, List<List<Object>> updates) {
        String message = null;
        
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
            //logger.info("[PROFILE CASE] - {}", messageText);
            System.out.println("[PROFILE] - " + messageText);
            if (checker.matches(messageText)) {
                //logger.info("[MATCH]");
                System.out.println("[MATCH]");
                return "1";
            } else {
                //logger.info("[NO MATCH]");
                System.out.println("[NO MATCH]");
                return "3";
            }
        } else if (isExpired(messageText, buttons)) {
            System.out.println("[EXPIRED CASE]");
            return "2";
        } else if (isNeedSubscription(messageText, buttons)) {
            System.out.println("[NEED SUBSCRIPTION CASE]");
            return "2";
        } else if (isNoTextInProfileWarn(messageText, buttons)) {
            System.out.println("[NO TEXT IN PROFILE WARN CASE]");
            return "1";
        } else if (isLikedBySomeone(messageText, buttons)) {
            System.out.println("[LIKED BY SOMEONE CASE]");
            return "1";
        } else if (isTooManyLikes(messageText, buttons)) {
            throw new TooManyLikesException();
            //return "1";
        } else if (isSleeping(messageText, buttons)) {
            System.out.println("[SLEEPING CASE]");
            return "1";
        } else if (isNewProfilesWantToMeet(messageText, buttons)) {
            System.out.println("[NEW PROFILES CASE]");
            return "1";
        } else if (isQuestion(messageText, buttons)) {
            System.out.println("[QUESTION/ADVERTISEMENT CASE]");
            return "2";
        } else if (isLocation(messageText, buttons)) {
            try {
                System.out.println("[LOCATION CASE]");
                //startSoundAlarm();
                ObjectMapper mapper = new ObjectMapper();
                String output = mapper.writeValueAsString(buttons);
                String output2 = mapper.writeValueAsString(updates);
                //System.out.println("[LOCATION DEBUG INFO]\nmessage=" + messageText + "\nbuttons=" + output);
                logger.error("- [LOCATION - DEBUG] \nmessage={}, \nbuttons={}, \nupdates={}", messageText, output, output2);
            } catch (JsonProcessingException ex) {
                logger.error(ex.getMessage());
            }
            return "1";
        } else {
            message = getCustomAnswer(updates);
        }

        return message;
    }

    public Set<Integer> getFlags(int sum) {
        Set<Integer> result = new HashSet<>();
        for (int flag : flags) {
            if ((flag & sum) == flag) {
                result.add(flag);
            }
        }

        return result;
    }

    private void printButtons(List<List<KeyboardButton>> buttonRows) {
        System.out.println("""
                           **********************
                           *       BUTTONS      *
                           **********************
                           """);
        for (List<KeyboardButton> row : buttonRows) {
            System.out.println(">>> NEW LINE");
            for (KeyboardButton button : row) {
                System.out.println("\t'PAYLOAD' = " + button.getAction().getPayload()
                        + " 'LABEL' = " + button.getAction().getLabel());
            }
        }
        System.out.println("**********************");
    }

    private String getCustomAnswer(List<List<Object>> updates) {
        String answer = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String output = mapper.writeValueAsString(updates);
            
            var conversation = VKUtil.getConversation(vk, actor, DAI_VINCHIK_BOT_CHAT_ID);
            
            var keyboard = conversation.getCurrentKeyboard();
            List<List<KeyboardButton>> buttonRows = keyboard.getButtons();
            printButtons(buttonRows);
            
            
            
            //System.out.println("[UNKNOWN STATE] updates=" + output);
            System.out.print("\nENTER CORRECT ANSWER:");
            startSoundAlarm();
            Scanner scanner = new Scanner(System.in);
            answer = scanner.nextLine();
            while (answer == null || answer.isEmpty() || answer.isBlank()) {
                System.out.print("ERROR: empty input, try again: ");
                answer = scanner.nextLine();
            }
            String history = vk.messages()
                    .getHistory(actor)
                    .peerId(DAI_VINCHIK_BOT_CHAT_ID)
                    .count(5)
                    .rev(GetHistoryRev.REVERSE_CHRONOLOGICAL)
                    .executeAsString();
            
            logger.error(" - UNKNOWN STATE: \nhistory={}, \nkeyboard={}, \nanswer={}, \nupdates={}",
                    history, keyboard.toPrettyString(), answer, output);

        } catch (ApiException | ClientException ex) {
            logger.error(ExceptionUtil.getFormattedDescription(ex));
        } catch (JsonProcessingException ex) {
            java.util.logging.Logger.getLogger(HandlerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return answer;
    }

}
