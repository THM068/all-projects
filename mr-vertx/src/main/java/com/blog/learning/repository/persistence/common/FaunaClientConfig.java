package com.blog.learning.repository.persistence.common;

import com.faunadb.client.FaunaClient;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;


import java.net.MalformedURLException;
@Factory

public class FaunaClientConfig {

    private FaunaClientProperties faunaProperties;

    /**
     * It initiates a singleton {@link FaunaClient} instance
     * using the settings defined at {@link FaunaClientProperties}.
     * This allows the {@link FaunaClient} to be properly injected
     * in any other application component.
     *
     * @return a singleton {@link FaunaClient} instance
     * @throws MalformedURLException if the provided endpoint is an invalid URL
     */@Bean
    public FaunaClient faunaClient() throws MalformedURLException {
        FaunaClient client =
            FaunaClient.builder()
                .withEndpoint(faunaProperties.getEndpoint())
                .withSecret(faunaProperties.getSecret())
                .build();

        return client;
    }

}
