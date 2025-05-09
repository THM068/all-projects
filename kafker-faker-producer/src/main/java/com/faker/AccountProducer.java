package com.faker;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface AccountProducer {

    @Topic("signup-events")
    void sendAccount(@KafkaKey String key, String payload); //;
}
