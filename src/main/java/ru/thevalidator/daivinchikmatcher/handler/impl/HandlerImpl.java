/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActorWithoutId;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.GetHistoryRev;
import com.vk.api.sdk.objects.messages.KeyboardButton;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import ru.thevalidator.daivinchikmatcher.exception.TooManyLikesException;
import ru.thevalidator.daivinchikmatcher.gui.AppWindow;
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
import ru.thevalidator.daivinchikmatcher.settings.Parameter;
import static ru.thevalidator.daivinchikmatcher.util.SoundUtil.startSoundAlarm;
import ru.thevalidator.daivinchikmatcher.util.EmojiCleaner;
import ru.thevalidator.daivinchikmatcher.util.ExceptionUtil;
import static ru.thevalidator.daivinchikmatcher.util.SoundUtil.startSounNotification;
import ru.thevalidator.daivinchikmatcher.util.VKUtil;

public class HandlerImpl implements Handler {

    private static final Logger logger = LogManager.getLogger(HandlerImpl.class);
    private static final int[] FLAGS = Flag.getAllFlagCodes();
    private static final boolean SHOULD_LIKE_ON_LIKE = (boolean) AppWindow.getSettings().get(Parameter.LIKE_ON_LIKE);
    private final Set<String> continueWords;
    private final MatchChecker checker;
    private VkApiClient vk;
    private UserActorWithoutId actor;
    private Informer informer;
    private int likes;

    public HandlerImpl(Set<Filter> filters, VkApiClient vk, UserActorWithoutId actor) {
        this.continueWords = FileUtil.readDict(Data.CONTINUE_WORDS);
        this.checker = new ProfileMatchCheckerImpl(filters);
        this.vk = vk;
        this.actor = actor;
        likes = 0;
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
        boolean hasLike = false;
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
                        //lastMsgIndex = i;
                        String message = o.get(5).toString();

                        if (message.startsWith("Есть взаимная симпатия")) {
                            Matcher matcher = Pattern.compile("([\\p{L}\\p{N}\\p{P}\\p{Z}]+ - )(?<link>([\\p{L}\\p{N}\\p{P}\\p{Z}]+)){1}(<br>|\\n){1,}.+").matcher(message);
                            //String res = "";
                            if (matcher.find()) {
                                message = matcher.group("link");
                            }
                            logger.info("{}", message);
                            //System.out.println("[MUTUAL LIKE - no kbrd] - " + message);
                            informer.informObservers(actor.getUserName()
                                    + "\n> [LIKE] " + message);
                            if ((boolean) AppWindow.getSettings().get(Parameter.SOUND_ALARM)) {
                                startSounNotification();
                            }

                        }

                        if (!message.contains("Заканчивай с вопросом выше ")) {
                            lastMsgIndex = i;
                        } else {
                            hasLike = true;
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
                if (message.endsWith("пришли мне свое местоположение и увидишь кто находится рядом")) {
                    return "1";
                } //else if (message.equals("Нашли кое-кого для тебя ;) Заканчивай с вопросом выше и увидишь кто это")) {
                //System.out.println("FOUND");
                //TODO: make normal handling messages without keyboard

                // ?????????
                //Кому-то понравилась твоя анкета! Заканчивай с вопросом выше и посмотрим кто это
                //Нашли кое-кого для тебя ;) Заканчивай с вопросом выше и увидишь кто это
                // ?????????
                //}
                //System.out.println(message);
                answer = getCustomAnswer(message, updates);
            } else {
                String textWithoutEmoji = EmojiCleaner.clean(message);
                answer = generateMessage(textWithoutEmoji, buttons, updates);
            }
        }

        if (hasLike) {
            updateLikes();
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
                                informer.informObservers(actor.getUserName() + "\n> [CONTINUE WORD FOUND]");
                                return b.getAction().getPayload();
                            }
                        }
                    }
                }
            }
        }

        if (isMutualLike(messageText, buttons)) {
            logger.info("[LIKE] - {}", messageText);
            informer.informObservers(actor.getUserName() + "\n> [LIKE] " + messageText);
            return "1";
        } else if (isProfile(messageText, buttons)) {
            String logText = null;

            if (messageText.startsWith("Кому-то понравилась твоя анкета")) {
                updateLikes();
            }

            if (likes > 0 || checker.matches(messageText)) {

                if (likes > 0) {
                    logText = actor.getUserName() + "\n> [PROFILE] - " + messageText + "\n> [LIKE ON LIKE]";
                    likes--;
                } else {
                    logText = actor.getUserName() + "\n> [PROFILE] - " + messageText + "\n> [MATCH]";
                }

                informer.informObservers(logText);
                message = "1";

            } else {
                informer.informObservers(actor.getUserName() + "\n> [PROFILE] - " + messageText + "\n> [NO MATCH]");
                for (Button button : buttons) {
                    if (button.getColor().equals("negative")) {
                        message = button.getAction().getPayload();
                    }
                }
            }
        } else if (isAdvertisement(messageText, buttons)) {
            informer.informObservers(actor.getUserName() + "\n> [ADVERTISEMENT CASE]");
            for (Button button : buttons) {
                if (button.getColor().equals("default")) {
                    message = button.getAction().getPayload();
                }
            }
        } else if (isNoTextInProfileWarn(messageText, buttons)) {
            informer.informObservers(actor.getUserName() + "\n> [NO TEXT IN PROFILE WARN CASE]");
            return "1";
        } else if (isLikedBySomeone(messageText, buttons)) {
            informer.informObservers(actor.getUserName() + "\n> [LIKED BY SOMEONE CASE]");
            updateLikes();
            return "1";
        } else if (isTooManyLikes(messageText, buttons)) {
            int hoursToSleep = 12;
            informer.informObservers(actor.getUserName() + "\n> [TOO MANY LIKES CASE] sleeping " + hoursToSleep + " hours");
            throw new TooManyLikesException();
        } else if (isSleeping(messageText, buttons)) {
            informer.informObservers(actor.getUserName() + "\n> [SLEEPING CASE]");
            return "1";
        } else if (isNewProfilesWantToMeet(messageText, buttons)) {
            informer.informObservers(actor.getUserName() + "\n> [NEW PROFILES CASE]");
            return "1";
        } else if (isLocation(messageText, buttons)) {
            informer.informObservers(actor.getUserName() + "\n> [LOCATION CASE]");
            return "1";
        } else if (isAdvise(messageText, buttons)) {
            informer.informObservers(actor.getUserName() + "\n> [ADVISE CASE]");
            return "2";
        } else {
            //experimental//

            // 1
            //===========
            //keyboard=Keyboard{buttons=[[{"action":{"label":"1","payload":"1","type":"text"},"color":"positive"}, {"action":{"label":"2","payload":"2","type":"text"},"color":"default"}, {"action":{"label":"3","payload":"3","type":"text"},"color":"default"}, {"action":{"label":"✈️ 4","payload":"4","type":"text"},"color":"default"}]], inline=false, authorId=-91050183, oneTime=false}, 
            //answer=1, 
            // 2
            //===========
            //keyboard=Keyboard{buttons=[[{"action":{"label":"❤️","payload":"1","type":"text"},"color":"positive"}, {"action":{"label":"💌","payload":"2","type":"text"},"color":"positive"}, {"action":{"label":"👎","payload":"3","type":"text"},"color":"negative"}, {"action":{"label":"💤","payload":"4","type":"text"},"color":"default"}]], inline=false, authorId=-91050183, oneTime=false}, 
            //answer=1, 
            //
            message = getCustomAnswer(messageText, updates);
        }

        return message;
    }

    public Set<Integer> getFlags(int sum) {
        Set<Integer> result = new HashSet<>();
        for (int flag : FLAGS) {
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

            if ((boolean) AppWindow.getSettings().get(Parameter.SOUND_ALARM)) {
                Thread t = new Thread(() -> {
                    startSoundAlarm();
                });
                t.start();
            }

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

    private void updateLikes() {
        if (SHOULD_LIKE_ON_LIKE) {
            likes++;
        }
    }

}
