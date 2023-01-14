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
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
import ru.thevalidator.daivinchikmatcher.notification.Informer;
import ru.thevalidator.daivinchikmatcher.parser.ResponseParser;
import ru.thevalidator.daivinchikmatcher.property.Data;
import static ru.thevalidator.daivinchikmatcher.property.Data.DAI_VINCHIK_BOT_CHAT_ID;
import static ru.thevalidator.daivinchikmatcher.util.AlarmUtil.startSoundAlarm;
import ru.thevalidator.daivinchikmatcher.util.ExceptionUtil;
import ru.thevalidator.daivinchikmatcher.util.VKUtil;

public class HandlerImpl implements Handler {

    private static final Logger logger = LogManager.getLogger(HandlerImpl.class);
    private static final int[] flags = Flag.getAllFlagCodes();
    private final Set<String> continueWords;
    private final MatchChecker checker;
    private VkApiClient vk;
    private UserActor actor;
    private Informer informer;

    public HandlerImpl(Set<Filter> filters, VkApiClient vk, UserActor actor) {
        this.continueWords = FileUtil.readDict(Data.CONTINUE_WORDS);
        this.checker = new ProfileMatchCheckerImpl(filters);
        this.vk = vk;
        this.actor = actor;
    }

    public void setInformer(Informer informer) {
        this.informer = informer;
    }

    @Override
    public String getStartMessage(String lastMessageText, List<Button> buttons) {
        if (isTooManyLikes(lastMessageText, buttons)) {
            return "1";
        }
        return generateMessage(lastMessageText, buttons, null);
    }

    @Override
    public String getAnswerMessage(List<List<Object>> updates) {
        String answer = null;

        Integer lastMsgIndex = null;
        for (int i = 0; i < updates.size(); i++) {
            List<Object> o = updates.get(i);
            Integer code = (Integer) o.get(0);

            if (code == Code.INCOMING_MESSAGE) {
                Integer flagsSum = (Integer) o.get(2);
                Set<Integer> messageFlags = getFlags(flagsSum);

                if (!messageFlags.contains(Flag.OUTBOX.getFlagCode()) && o.size() == 8) {
                    Integer minorId = (Integer) o.get(3);

                    if (minorId == DAI_VINCHIK_BOT_CHAT_ID) {
                        lastMsgIndex = i;
                        String message = o.get(5).toString();
                        if (message.startsWith("Есть взаимная симпатия")) {
                            Matcher matcher = Pattern.compile("([\\p{L}\\p{N}\\p{P}\\p{Z}]+ - )(?<link>([\\p{L}\\p{N}\\p{P}\\p{Z}]+)){1}(<br>|\\n){1,}.+").matcher(message);
                            //String res = "";
                            if (matcher.find()) {
                                message = matcher.group("link");
                            }
                            logger.info("{}", message);
                            //System.out.println("[MUTUAL LIKE - no kbrd] - " + message);
                            informer.informObservers(Thread.currentThread().getName()
                                    + "\n> [LIKE] " + message);
                            //startSoundAlarm();
                        }
                    }

                }

            }

        }

        if (lastMsgIndex != null) {
            List<Object> lastMsgData = updates.get(lastMsgIndex);
            String message = lastMsgData.get(5).toString();
            Object keyboardData = lastMsgData.get(6);

            Keyboard actualKeyboard = ResponseParser.parseKeyboard(keyboardData);
            //System.out.println("KBRD");
            //System.out.println(actualKeyboard == null);
            List<Button> buttons = actualKeyboard != null
                    ? actualKeyboard.getButtons().get(actualKeyboard.getButtons().size() - 1)
                    : null;

            //
            //System.out.println(">>> " + message);
            //System.out.println(">>> " + (buttons != null ? buttons.size() : "null"));
            //

            if (buttons == null) {
                if (message.endsWith("пришли мне свое местоположение и увидишь кто находится рядом")
                        || message.equals("Нашли кое-кого для тебя ;) Заканчивай с вопросом выше и увидишь кто это")) {
                    return "1";
                } else if (message.equals("Нашли кое-кого для тебя ;) Заканчивай с вопросом выше и увидишь кто это")) {
                    //System.out.println("FOUND");
                    //TODO: make normal handling messages without keyboard
                }
                //System.out.println(message);
                answer = getCustomAnswer(message, updates);
            } else {
                answer = generateMessage(message, buttons, updates);
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
                                //System.out.println("[CONTINUE WORD FOUND]");
                                informer.informObservers(Thread.currentThread().getName()
                                        + "\n> [CONTINUE WORD FOUND]");
                                return b.getAction().getPayload();
                            }
                        }
                    }
                }
            }
        }

//        if (messageText.startsWith("Время просмотра анкеты истекло")) {
//            messageText = messageText.replaceFirst("Время просмотра анкеты истекло, действие не выполнено.(<br><br>|\n\n)", "");
//        }

        //System.out.println("IDENTIFY");
        if (isMutualLike(messageText, buttons)) {
            logger.info("[LIKE] - {}", messageText);
            //System.out.println("[MUTUAL LIKE] - " + messageText);
            informer.informObservers(Thread.currentThread().getName()
                    + "\n> [LIKE] " + messageText);
            //startSoundAlarm();
            return "1";
        } else if (isProfile(messageText, buttons)) {
            //getCustomAnswer(messageText, updates);
            //System.out.println("[PROFILE] - " + messageText);
            
            String msg = Thread.currentThread().getName()
                    + "\n> [PROFILE] - " + messageText;
            if (checker.matches(messageText)) {
                //System.out.println("[MATCH]");
                informer.informObservers(msg
                        + "\n> [MATCH]");
                message = "1";
            } else {
                //System.out.println("[NO MATCH]");
                informer.informObservers(msg
                        + "\n> [NO MATCH]");
                for (Button button : buttons) {
                    if (button.getColor().equals("negative")) {
                        message = button.getAction().getPayload();
                    }
                }
            }
        } else if (isAdvertisement(messageText, buttons)) {
            //System.out.println("[TELEGRAM INVITE CASE]");
            informer.informObservers(Thread.currentThread().getName()
                    + "\n> [ADVERTISEMENT CASE]");
            //startSoundAlarm();
            for (Button button : buttons) {
                if (button.getColor().equals("default")) {
                    message = button.getAction().getPayload();
                }
            }
            //return "2";
        } else if (isNoTextInProfileWarn(messageText, buttons)) {
            //System.out.println("[NO TEXT IN PROFILE WARN CASE]");
            informer.informObservers(Thread.currentThread().getName()
                    + "\n> [NO TEXT IN PROFILE WARN CASE]");
            return "1";
        } else if (isLikedBySomeone(messageText, buttons)) {
            //System.out.println("[LIKED BY SOMEONE CASE]");
            informer.informObservers(Thread.currentThread().getName()
                    + "\n> [LIKED BY SOMEONE CASE]");
            //startSoundAlarm();
            return "1";
        } else if (isTooManyLikes(messageText, buttons)) {
            //System.out.println("[TOO MANY LIKES]");
            informer.informObservers(Thread.currentThread().getName()
                    + "\n> [TOO MANY LIKES CASE] sleeping 24 hours");
            try {
                TimeUnit.DAYS.sleep(1);
                //throw new TooManyLikesException();
            } catch (InterruptedException ex) {
                //nothing to do
            }
        } else if (isSleeping(messageText, buttons)) {
            //System.out.println("[SLEEPING CASE]");
            informer.informObservers(Thread.currentThread().getName()
                    + "\n> [SLEEPING CASE]");
            return "1";
        } else if (isNewProfilesWantToMeet(messageText, buttons)) {
            //System.out.println("[NEW PROFILES CASE]");
            informer.informObservers(Thread.currentThread().getName()
                    + "\n> [NEW PROFILES CASE]");
            //startSoundAlarm();
            return "1";
//        } else if (isQuestion(messageText, buttons)) {
//            System.out.println("[QUESTION/ADVERTISEMENT CASE]");
//            startSoundAlarm();
//            return "2";
        } else if (isLocation(messageText, buttons)) {
//            try {
                //System.out.println("[LOCATION CASE]");
                informer.informObservers(Thread.currentThread().getName()
                        + "\n> [LOCATION CASE]");
                //startSoundAlarm();
//                ObjectMapper mapper = new ObjectMapper();
//                String output = mapper.writeValueAsString(buttons);
//                String output2 = mapper.writeValueAsString(updates);
                //System.out.println("[LOCATION DEBUG INFO]\nmessage=" + messageText + "\nbuttons=" + output);
//                logger.error(" - [LOCATION - DEBUG] \nmessage={}, \nbuttons={}, \nupdates={}", messageText, output, output2);
//            } catch (JsonProcessingException ex) {
//                logger.error(ex.getMessage());
//            }
            return "1";
        } else {
            message = getCustomAnswer(messageText, updates);
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

    public String getCustomAnswer(String messageText, List<List<Object>> updates) {

        String answer = null;

        try {

            ObjectMapper mapper = new ObjectMapper();
            String longPollResponse = mapper.writeValueAsString(updates);

            var conversation = VKUtil.getConversation(vk, actor, DAI_VINCHIK_BOT_CHAT_ID);

            var keyboard = conversation.getCurrentKeyboard();
            List<List<KeyboardButton>> buttonRows = keyboard.getButtons();

            Thread t = new Thread(() -> {
                startSoundAlarm();
            });
            t.start();

            while (answer == null) {
                answer = getUserAnswer(messageText, buttonRows);
            }

            String history = vk.messages()
                    .getHistory(actor)
                    .peerId(DAI_VINCHIK_BOT_CHAT_ID)
                    .count(3)
                    .rev(GetHistoryRev.REVERSE_CHRONOLOGICAL)
                    .executeAsString();

            logger.error(" - UNKNOWN STATE: \nhistory={}, \nkeyboard={}, \nanswer={}, \nupdates={}",
                    history, keyboard.toPrettyString(), answer, longPollResponse);

        } catch (ApiException | ClientException | JsonProcessingException ex) {
            logger.error(ExceptionUtil.getFormattedDescription(ex));
        }

        return answer;
    }

    private String getUserAnswer(String messageText, List<List<KeyboardButton>> buttonRows) {

        String input = null;

        Set<String> payloads = new HashSet<>();
        StringBuilder sb = new StringBuilder(messageText + "\n");

        for (List<KeyboardButton> row : buttonRows) {
            for (KeyboardButton button : row) {
                payloads.add(button.getAction().getPayload());
                sb.append("\nLABEL = ").append(button.getAction().getLabel())
                        .append(" : PAYLOAD = ").append(button.getAction().getPayload());
            }
        }

        JTextArea jTextArea = new JTextArea(sb.toString());
        JTextField answerField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(jTextArea);
        panel.add(new JLabel("Payload"));
        panel.add(answerField);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Give answer",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String answer = answerField.getText().trim();
            if (payloads.contains(answer)) {
                return answer;
            }

        }

        return input;

    }

}
