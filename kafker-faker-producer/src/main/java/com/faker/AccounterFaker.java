package com.faker;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import net.datafaker.Faker;

@Singleton
public class AccounterFaker {
    private final AccountProducer accountProducer;
    private final Faker faker = new Faker();

    public AccounterFaker(AccountProducer accountProducer) {
        this.accountProducer = accountProducer;
    }

    @Scheduled(fixedDelay = "10s")
    void executeEveryTen() {
        final long accountId = faker.number().numberBetween(100L, 30000L);
        final String email = faker.internet().emailAddress();
        String account = String.format("{\"accountId\":%s,\"email\":\"%s\"}",accountId,email);
        accountProducer.sendAccount(String.valueOf(accountId), account);
        System.out.println("Executing every 10 seconds email: " + account);
    }
}
