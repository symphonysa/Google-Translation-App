package com.example.testbot.bot;

import com.example.testbot.SymphonyTestConfiguration;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;
import com.neovisionaries.i18n.LanguageAlpha3Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.symphonyoss.client.SymphonyClient;
import org.symphonyoss.client.model.Chat;
import org.symphonyoss.client.services.ChatListener;
import org.symphonyoss.client.services.ChatServiceListener;
import org.symphonyoss.client.services.RoomService;
import org.symphonyoss.symphony.clients.model.SymMessage;
import org.symphonyoss.symphony.clients.model.SymUser;
import java.util.*;

public class TranslateBot implements ChatListener, ChatServiceListener {
    private Map<Long, String> userProfiles = new HashMap<>();
    private static TranslateBot instance;
    private final Logger logger = LoggerFactory.getLogger(TranslateBot.class);
    private SymphonyClient symClient;
    private RoomService roomService;
    SymphonyTestConfiguration config;


    protected TranslateBot(SymphonyClient symClient, SymphonyTestConfiguration config) {
        this.symClient=symClient;
        this.config = config;
        init();
    }

    public static TranslateBot getInstance(SymphonyClient symClient, SymphonyTestConfiguration config){
        if(instance==null){
            instance = new TranslateBot(symClient,config);
        }
        return instance;
    }

    private void init() {

        logger.info("Connections example starting...");

        symClient.getChatService().addListener(this);





    }

    public void sendTranslation(String message, Long fromUserID) throws Exception {


        try {
            // See comments on
            //   https://developers.google.com/resources/api-libraries/documentation/translate/v2/java/latest/
            // on options to set
            Translate t = new Translate.Builder(
                    com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport()
                    , com.google.api.client.json.gson.GsonFactory.getDefaultInstance(), null)
                    //Need to update this to your App-Name
                    .setApplicationName("Stackoverflow-Example")
                    .build();

            Set<String> langs = new HashSet<>();
            Map<String, String> translations = new HashMap<>();
            for(String userlangs : userProfiles.values()){
                langs.add(userlangs);
            }
            for(String lang : langs) {
                Translate.Translations.List list = t.new Translations().list(
                        Arrays.asList(message),
                        //Target language
                        lang);
                //Set your API-Key from https://console.developers.google.com/
                list.setKey("AIzaSyA5Fzl1SZRoimZxDFD1ClruwwvCGruBJ-o");
                TranslationsListResponse response = list.execute();
                for(TranslationsResource tr : response.getTranslations()) {
                    translations.put(lang, tr.getTranslatedText());
                }
            }


            for(Long userid : userProfiles.keySet()){
                if( !userid.equals(fromUserID) ) {
                    Chat chat = new Chat();
                    chat.setLocalUser(symClient.getLocalUser());
                    Set<SymUser> remoteUsers = new HashSet<>();
                    remoteUsers.add(symClient.getUsersClient().getUserFromId(userid));
                    chat.setRemoteUsers(remoteUsers);
                    //chat.addListener(this);
                    chat.setStream(symClient.getStreamsClient().getStream(remoteUsers));
                    //A message to send when the BOT comes online.
                    SymMessage aMessage = new SymMessage();
                    aMessage.setMessageText(translations.get(userProfiles.get(userid)));
                    symClient.getMessageService().sendMessage(chat, aMessage);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onChatMessage(SymMessage message) {
        SymMessage commandMsg = new SymMessage();
        try {
            if (message.getMessage().contains("/list")) {
                List<String> languages = new ArrayList<>();
                languages.add("English");
                languages.add("Spanish");
                languages.add("French");

                String availLang = "Available Languages are: ";
                for( String lang :languages) {
                    availLang = availLang + lang + ", ";
                }

                commandMsg.setMessageText(availLang);
                //Send a message to the master user.
                symClient.getMessageService().sendMessage(message.getStream(), commandMsg);

            } else if (message.getMessage().contains("/set")) {
                String[] arr = message.getMessageText().split(" ");
                List<LanguageAlpha3Code> list = LanguageAlpha3Code.findByName(arr[1]);
                if (list.size() != 0 ) {
                    userProfiles.put(message.getFromUserId(), list.get(list.size() - 1).toString());

                    commandMsg.setMessageText("Language saved as " + arr[1]);
                    //Send a message to the master user.
                    symClient.getMessageService().sendMessage(message.getStream(), commandMsg);
                } else {
                    commandMsg.setMessageText("Sorry, language could not be found.");
                    //Send a message to the master user.
                    symClient.getMessageService().sendMessage(message.getStream(), commandMsg);
                }
            } else if (message.getMessage().contains("/send")) {
                SymUser fromUser = symClient.getUsersClient().getUserFromId(message.getFromUserId());
                String cleanedMessage = message.getMessageText().substring(5);
                sendTranslation("(" + fromUser.getDisplayName() + ") " + cleanedMessage, message.getFromUserId());

            } else {
                commandMsg.setMessageText("Command Not Recognized. Please use /list /set /send");
                //Send a message to the master user.
                symClient.getMessageService().sendMessage(message.getStream(), commandMsg);

            }

        } catch (Exception e) {
            logger.error("Error translating message", e);
        }
    }

    @Override
    public void onNewChat(Chat chat) {
        chat.addListener(this);
    }

    @Override
    public void onRemovedChat(Chat chat) {

    }

}
