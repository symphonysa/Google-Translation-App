package com.example.testbot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;

public class SymphonyTestConfiguration extends Configuration {

    @NotEmpty
    private String sessionAuthURL;

    @NotEmpty
    private String keyAuthUrl;
    @NotEmpty
    private String localKeystorePath;
    @NotEmpty
    private String localKeystorePassword;
    @NotEmpty
    private String botCertPath;
    @NotEmpty
    private String botCertPassword;
    @NotEmpty
    private String botEmailAddress;
    @NotEmpty
    private String userEmailAddress;

    @NotEmpty
    private String agentAPIEndpoint;
    @NotEmpty
    private String podAPIEndpoint;

    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @NotNull
    private Map<String, Map<String, String>> viewRendererConfiguration = Collections.emptyMap();

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    @JsonProperty("viewRendererConfiguration")
    public Map<String, Map<String, String>> getViewRendererConfiguration() {
        return viewRendererConfiguration;
    }

    @JsonProperty("viewRendererConfiguration")
    public void setViewRendererConfiguration(Map<String, Map<String, String>> viewRendererConfiguration) {
        final ImmutableMap.Builder<String, Map<String, String>> builder = ImmutableMap.builder();
        for (Map.Entry<String, Map<String, String>> entry : viewRendererConfiguration.entrySet()) {
            builder.put(entry.getKey(), ImmutableMap.copyOf(entry.getValue()));
        }
        this.viewRendererConfiguration = builder.build();
    }

    @JsonProperty
    public String getSessionAuthURL() {
        return sessionAuthURL;
    }

    @JsonProperty("sessionAuthURL")
    public void setSessionAuthURL(String sessionAuthURL) {
        this.sessionAuthURL = sessionAuthURL;
    }

    @JsonProperty
    public String getKeyAuthUrl() {
        return keyAuthUrl;
    }

    @JsonProperty("keyAuthUrl")
    public void setKeyAuthUrl(String keyAuthUrl) {
        this.keyAuthUrl = keyAuthUrl;
    }

    @JsonProperty
    public String getLocalKeystorePath() {
        return localKeystorePath;
    }

    @JsonProperty("localKeystorePath")
    public void setLocalKeystorePath(String localKeystorePath) {
        this.localKeystorePath = localKeystorePath;
    }

    @JsonProperty
    public String getLocalKeystorePassword() {
        return localKeystorePassword;
    }

    @JsonProperty("localKeystorePassword")
    public void setLocalKeystorePassword(String localKeystorePassword) {
        this.localKeystorePassword = localKeystorePassword;
    }

    @JsonProperty
    public String getBotCertPath() {
        return botCertPath;
    }

    @JsonProperty("botCertPath")
    public void setBotCertPath(String botCertPath) {
        this.botCertPath = botCertPath;
    }

    @JsonProperty
    public String getBotCertPassword() {
        return botCertPassword;
    }

    @JsonProperty("botCertPassword")
    public void setBotCertPassword(String botCertPassword) {
        this.botCertPassword = botCertPassword;
    }

    @JsonProperty
    public String getBotEmailAddress() {
        return botEmailAddress;
    }

    @JsonProperty("botEmailAddress")
    public void setBotEmailAddress(String botEmailAddress) {
        this.botEmailAddress = botEmailAddress;
    }

    @JsonProperty
    public String getAgentAPIEndpoint() {
        return agentAPIEndpoint;
    }

    @JsonProperty("agentAPIEndpoint")
    public void setAgentAPIEndpoint(String agentAPIEndpoint) {
        this.agentAPIEndpoint = agentAPIEndpoint;
    }

    @JsonProperty
    public String getPodAPIEndpoint() {
        return podAPIEndpoint;
    }

    @JsonProperty("podAPIEndpoint")
    public void setPodAPIEndpoint(String podAPIEndpoint) {
        this.podAPIEndpoint = podAPIEndpoint;
    }

    @JsonProperty
    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    @JsonProperty("userEmailAddress")
    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

}
