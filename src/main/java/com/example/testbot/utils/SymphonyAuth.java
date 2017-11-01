package com.example.testbot.utils;

import com.example.testbot.SymphonyTestConfiguration;
import org.symphonyoss.client.SymphonyClient;
import org.symphonyoss.client.SymphonyClientFactory;
import org.symphonyoss.client.model.SymAuth;
import org.symphonyoss.symphony.clients.AuthenticationClient;


/**
 * Created by mike.scannell on 11/7/16.
 */

public class SymphonyAuth {

    public SymphonyClient init(SymphonyTestConfiguration config) throws Exception{

        SymphonyClient symClient;

        symClient = SymphonyClientFactory.getClient(SymphonyClientFactory.TYPE.V4);

        //Init the Symphony authorization client, which requires both the key and session URL's.  In most cases,
        //the same fqdn but different URLs.
        AuthenticationClient authClient = new AuthenticationClient(config.getSessionAuthURL(), config.getKeyAuthUrl());


        //Set the local keystores that hold the server CA and client certificates
        authClient.setKeystores(
                config.getLocalKeystorePath(),
                config.getLocalKeystorePassword(),
                config.getBotCertPath(),
                config.getBotCertPassword());

        SymAuth symAuth = authClient.authenticate();


        //With a valid SymAuth we can now init our client.
        symClient.init(
                symAuth,
                config.getBotEmailAddress(),
                config.getAgentAPIEndpoint(),
                config.getPodAPIEndpoint()
        );

        return symClient;

    }



}