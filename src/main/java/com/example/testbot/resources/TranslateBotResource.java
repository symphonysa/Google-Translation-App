package com.example.testbot.resources;

import com.example.testbot.SymphonyTestConfiguration;
import com.example.testbot.bot.TranslateBot;
import com.example.testbot.utils.SymphonyAuth;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.api.services.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.neovisionaries.i18n.LanguageAlpha3Code;
import freemarker.ext.beans.HashAdapter;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.symphonyoss.client.SymphonyClient;
import org.symphonyoss.client.model.Chat;
import org.symphonyoss.client.model.Room;
import org.symphonyoss.client.services.ChatListener;
import org.symphonyoss.symphony.agent.model.Datafeed;
import org.symphonyoss.symphony.agent.model.V2BaseMessage;
import org.symphonyoss.symphony.agent.model.V2Message;
import org.symphonyoss.symphony.clients.model.SymMessage;
import org.symphonyoss.symphony.clients.model.SymUser;
import org.symphonyoss.symphony.pod.model.Stream;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mike.scannell on 11/11/16.
 */

@Path("/symph-test")
public class TranslateBotResource {

    private Map<Long, String> userProfiles = new HashMap<>();
    private final Logger LOG = LoggerFactory.getLogger(TranslateBotResource.class);
    private SymphonyTestConfiguration config;
    private SymphonyClient symClient;
    private TranslateBot translateBot;

    public TranslateBotResource(SymphonyTestConfiguration config) {
        this.config = config;
        try {
            SymphonyClient symClient = new SymphonyAuth().init(config);
            translateBot = TranslateBot.getInstance(symClient, config);

        } catch (Exception e) {
            LOG.error("error", e);
        }
    }

}
