/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.v2.identity.impl;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.CustomUserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Conversation;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.KeyboardButton;
import com.vk.api.sdk.objects.messages.KeyboardButtonColor;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.daivinchikmatcher.exception.TooManyLikesException;
import ru.thevalidator.daivinchikmatcher.gui.AppWindow;
import ru.thevalidator.daivinchikmatcher.handler.QueryBuilder;
import ru.thevalidator.daivinchikmatcher.property.Data;
import static ru.thevalidator.daivinchikmatcher.property.Data.DAI_VINCHIK_BOT_CHAT_ID;
import ru.thevalidator.daivinchikmatcher.settings.Parameter;
import ru.thevalidator.daivinchikmatcher.util.FileUtil;
import ru.thevalidator.daivinchikmatcher.v2.identity.Handler;
import ru.thevalidator.daivinchikmatcher.matcher.ProfileMatcher;
import ru.thevalidator.daivinchikmatcher.notification.Informer;
import ru.thevalidator.daivinchikmatcher.util.DBUtil;
import ru.thevalidator.daivinchikmatcher.util.EmojiCleaner;
import ru.thevalidator.daivinchikmatcher.util.ExceptionUtil;
import static ru.thevalidator.daivinchikmatcher.util.SoundUtil.playAlarm;
import static ru.thevalidator.daivinchikmatcher.util.SoundUtil.playNotification;
import ru.thevalidator.daivinchikmatcher.util.TimestampUtil;
import ru.thevalidator.daivinchikmatcher.v2.identity.CaseMatcher;

public class Handlermpl implements Handler {

    private static final Logger logger = LogManager.getLogger(Handlermpl.class);
    private static final boolean SHOULD_LIKE_ON_LIKE = (boolean) AppWindow.getSettings().get(Parameter.LIKE_ON_LIKE);
    private static final boolean HAS_EXPERIMENTAL_OPTION = (boolean) AppWindow.getSettings().get(Parameter.EXPERIMENTAL_HANDLER);
    private static final boolean IS_DEBUG_MODE = (boolean) AppWindow.getSettings().get(Parameter.DEBUG_MODE);
    private static final Set<String> continueWords = FileUtil.readDict(Data.CONTINUE_WORDS);
    private final VkApiClient vk;
    private final CustomUserActor actor;
    private final QueryBuilder builder;
    private ProfileMatcher profileMatcher;
    private int likes;
    private Informer informer;

    public Handlermpl(VkApiClient vk, CustomUserActor actor, ProfileMatcher matchChecker) {
        this.vk = vk;
        this.actor = actor;
        this.profileMatcher = matchChecker;
        this.builder = new QueryBuilder(vk, actor);
        this.likes = 0;
    }

    public void setInformer(Informer informer) {
        this.informer = informer;
    }

    @Override
    public void handleLastMessage() {
        try {

            var conversationResponse = vk.messages().getConversationsById(actor, DAI_VINCHIK_BOT_CHAT_ID).execute();
            var dvConversationData = conversationResponse.getItems().get(0);

            Keyboard currentKeyboard = dvConversationData.getCurrentKeyboard();

            String answer = findKeyboardContinueWord(currentKeyboard);
            if (answer != null) {
                sendAnswer(builder.buildDVAnswer(answer));
                informer.informObservers(actor.getUserName() + "\n> [CONTINUE WORD FOUND]");
            } else {
                Message message = null;
                Integer lastMessageId = dvConversationData.getLastConversationMessageId();

                while (lastMessageId > 0) {
                    var messageResponse = vk.messages().getByConversationMessageId(actor, DAI_VINCHIK_BOT_CHAT_ID, lastMessageId).execute();
                    Message m = messageResponse.getItems().get(0);
                    if (m.getFromId() == DAI_VINCHIK_BOT_CHAT_ID) {
                        if (m.getText().contains("аканчивай с вопросом выше")) {
                            addLikes(1);
                        } else {
                            message = m;
                            break;
                        }
                    }
                    lastMessageId--;
                }

                if (message != null) {
                    String messageText = message.getText();
                    messageText = EmojiCleaner.clean(messageText);
                    List<KeyboardButton> buttons = getAllKeyboardButtons(currentKeyboard);

                    if (CaseMatcher.isMutualLike(messageText, buttons)) {

                        Matcher matcher = Pattern.compile("([\\p{L}\\p{N}\\p{P}\\p{Z}]+ - vk.com/id)(?<usrId>([\\p{Nd}]+)){1}(<br>|\\n){1,}.+")
                                .matcher(messageText);

                        if (matcher.find()) {
                            messageText = matcher.group("usrId");
                            logger.info("{}", messageText);

//                            DBUtil.insertLikeByUserVkId(String.valueOf(actor.getId()),
//                                    messageText,
//                                    String.valueOf(TimestampUtil.getTimestampOfNow()));
                            if ((boolean) AppWindow.getSettings().get(Parameter.SOUND_ALARM)) {
                                playNotification();
                            }

                        } else {
                            logger.error("[MUTUAL LIKE] - not found link in msg: {}", messageText);
                        }
                        sendAnswer(builder.buildDVAnswer("1"));
                        informer.informObservers(actor.getUserName() + "\n> [MUTUAL LIKE CASE]");
                        //System.out.println("[MUTUAL LIKE - no kbrd] - " + messageText);
                        //informer.informObservers(actor.getUserName() + "\n> [LIKE] " + messageText);

                    } else if (CaseMatcher.isProfile(messageText, buttons)) {
                        String consoleMessage = actor.getUserName() + "\n> [PROFILE] - " + messageText;
                        if (likes > 0) {
                            decreaseLikes();
                            sendAnswer(builder.buildDVAnswer("1"));
                            informer.informObservers(consoleMessage + "\n> [LIKE ON LIKE]");
                        } else if (profileMatcher.matches(messageText)) {
                            sendAnswer(builder.buildDVAnswer("1"));
                            informer.informObservers(consoleMessage + "\n> [MATCH]");
                        } else {
                            for (KeyboardButton button : buttons) {
                                if (button.getColor().equals(KeyboardButtonColor.NEGATIVE)) {
                                    sendAnswer(builder.buildDVAnswer(button.getAction().getPayload()));
                                    informer.informObservers(consoleMessage + "\n> [NO MATCH]");
                                    break;
                                }
                            }
                        }
                        
                    } else if (CaseMatcher.isAdvertisement(messageText, buttons)) {
                        for (KeyboardButton button : buttons) {
                            if (button.getColor().equals(KeyboardButtonColor.DEFAULT)) {
                                sendAnswer(builder.buildDVAnswer(button.getAction().getPayload()));
                                informer.informObservers(actor.getUserName() + "\n> [ADVERTISEMENT CASE]");
                                break;
                            }
                        }
                    } else if (CaseMatcher.isNoTextInProfileWarn(messageText, buttons)) {
                        sendAnswer(builder.buildDVAnswer("1"));
                        informer.informObservers(actor.getUserName() + "\n> [NO TEXT IN PROFILE WARN CASE]");
                    } else if (CaseMatcher.isLikedBySomeone(messageText, buttons)) {
                        Pattern p = Pattern.compile("[\\p{Nd}]+");
                        Matcher m = p.matcher(messageText);
                        if (m.find()) {
                            addLikes(Integer.parseInt(m.group()));
                        }
                        sendAnswer(builder.buildDVAnswer("1"));
                        informer.informObservers(actor.getUserName() + "\n> [LIKED BY SOMEONE CASE]");
                    } else if (CaseMatcher.isTooManyLikes(messageText, buttons)) {
                        informer.informObservers(actor.getUserName() + "\n> [TOO MANY LIKES CASE]");
                        throw new TooManyLikesException();
                    } else if (CaseMatcher.isNewProfilesWantToMeet(messageText, buttons)) {
                        sendAnswer(builder.buildDVAnswer("1"));
                        informer.informObservers(actor.getUserName() + "\n> [NEW PROFILES CASE]");
                    } else if (CaseMatcher.isSleeping(messageText, buttons)) {
                        sendAnswer(builder.buildDVAnswer("1"));
                        informer.informObservers(actor.getUserName() + "\n> [SLEEPING CASE]");
                    } else if (CaseMatcher.isAdvise(messageText, buttons)) {
                        sendAnswer(builder.buildDVAnswer("2"));
                        informer.informObservers(actor.getUserName() + "\n> [ADVISE CASE]");
                    } else if (CaseMatcher.isLocation(messageText, buttons)) {
                        sendAnswer(builder.buildDVAnswer("1"));
                        informer.informObservers(actor.getUserName() + "\n> [LOCATION CASE]");
                    } else {
                        if (HAS_EXPERIMENTAL_OPTION) {
                            if (messageText.contains("1. Смотреть анкеты.")) {

                                //informer.informObservers(actor.getUserName() + "\n> [EXPERIMENTAL CASE FOUND]");
                                logger.error(" [{}] - EXPERIMENTAL CASE FOUND \nmessage={}",
                                        AppWindow.APP_VER, messageText);
                                sendAnswer(builder.buildDVAnswer("1"));
                                informer.informObservers(actor.getUserName() + "\n> [EXPERIMENTAL CASE]");
                                return;
                            }
                        }

                        //TODO: GET ANSWER FROM USER
                        if ((boolean) AppWindow.getSettings().get(Parameter.SOUND_ALARM)) {
                            Thread t = new Thread(() -> {
                                playAlarm();
                            });
                            t.start();
                        }
                        while (answer == null) {
                            answer = getUserAnswer(messageText, buttons);
                        }
                        sendAnswer(builder.buildDVAnswer(answer));

                    }

                    //answer = findAnswer(currentKeyboard, message.getText());
                    //sendAnswer(builder.buildDVAnswer(answer));
                } else {
                    //TODO Throw exception ? throw new UnsupportedOperationException("Not supported yet.");
                }
            }

        } catch (Exception e) {
        }

    }

    @Override
    public void handleReplies(List<Integer> likes) {
        try {
            var res = vk.messages().getConversationsById(actor, likes).execute();
            for (Conversation item : res.getItems()) {
                if (item.getLastMessageId() != 0 && item.getCanWrite().getAllowed()) {
                    DBUtil.setHasReplyTrue(actor.getId(), item.getPeer().getId());
                    var response = builder.buildReplyForLikedUser(item.getPeer().getId()).executeAsRaw();
                    if (response.getStatusCode() == 200) {
                        DBUtil.setWasInvited(actor.getId(), item.getPeer().getId());
                        DBUtil.deleteAllLikesFromLikedIdWithoutReply(item.getPeer().getId());
                    } else {
                        logger.error("Couldn't reply id:" + item.getPeer().getId() + ". Code " + response.getStatusCode());
                    }
                    TimeUnit.SECONDS.sleep(6);
                }   
            }
        } catch (ApiException | ClientException | InterruptedException ex) {
            logger.error(ExceptionUtil.getFormattedDescription(ex));
        }
    }

    private void sendAnswer(MessagesSendQuery query) throws ClientException {
        ClientResponse response = query.executeAsRaw();
        if (IS_DEBUG_MODE) {
            logger.info(" SEND_RESPONSE " + response.getContent());
        }
//        if () {
//            //TODO: CAPTCHA HANDLE
//        }S
    }

    public List<KeyboardButton> getAllKeyboardButtons(Keyboard keyboard) {
        List<KeyboardButton> buttons = new ArrayList<>();
        for (List<KeyboardButton> row : keyboard.getButtons()) {
            for (KeyboardButton button : row) {
                buttons.add(button);
            }
        }
        return buttons.isEmpty() ? null : buttons;
    }

    //@Override
    public String findKeyboardContinueWord(Keyboard keyboard) {
        for (List<KeyboardButton> row : keyboard.getButtons()) {
            for (KeyboardButton button : row) {
                String btnLabel = button.getAction().getLabel();
                //System.out.println("btn >> " + button.toPrettyString());
                if (continueWords.contains(btnLabel)) {
                    return button.getAction().getPayload();
                }
            }
        }
        return null;
    }

    private void addLikes(int number) {
        if (SHOULD_LIKE_ON_LIKE) {
            likes += number;
        }
    }

    private void decreaseLikes() {
        if (likes > 0) {
            likes--;
        }
    }

    //@Override
    public boolean isLastMessage(String messageText) {
        return !messageText.contains("аканчивай с вопросом выше");
    }

    private String getUserAnswer(String messageText, List<KeyboardButton> buttons) {

        String input = null;

        Set<String> payloads = new HashSet<>();
        StringBuilder sb = new StringBuilder(messageText + "\n");

        for (KeyboardButton button : buttons) {
            payloads.add(button.getAction().getPayload());
            sb.append("\nLABEL = ").append(button.getAction().getLabel())
                    .append(" : PAYLOAD = ").append(button.getAction().getPayload());
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
