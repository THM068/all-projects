package com.blog.learning.repository.persistence.common;


import io.micronaut.context.annotation.ConfigurationProperties;

/**
 * It contains basic settings for initiating
 * a {@link com.faunadb.client.FaunaClient}.
 */

@ConfigurationProperties("fauna-db")
public class FaunaClientProperties {
    private String endpoint;
    private String secret;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
