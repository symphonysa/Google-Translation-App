package com.example.testbot;

import com.example.testbot.resources.TranslateBotResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;


public class SymphonyTestApplication extends Application<SymphonyTestConfiguration> {
    public static void main(String[] args) throws Exception {
        new SymphonyTestApplication().run(args);
    }

    @Override
    public String getName() {
        return "translate-bot";
    }

    @Override
    public void initialize(Bootstrap<SymphonyTestConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(SymphonyTestConfiguration configuration, Environment environment) throws Exception{
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new TranslateBotResource(configuration));
    }
}
