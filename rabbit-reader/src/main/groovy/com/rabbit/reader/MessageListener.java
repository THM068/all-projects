package com.rabbit.reader;

import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;

@RabbitListener
public class MessageListener {

    @Queue(value = "mfa-queue")
    public void receive(byte[] data) {
        String message = new String(data);
        System.out.println(message);

            System.out.println(message);
    }
}
