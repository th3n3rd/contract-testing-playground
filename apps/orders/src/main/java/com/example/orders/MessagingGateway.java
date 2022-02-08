package com.example.orders;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
class MessagingGateway {

    private final StreamBridge streamBridge;

    MessagingGateway(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    void send(Object message) {
        streamBridge.send(MessagingBindings.Out, MessageBuilder
            .withPayload(message)
            .setHeader("type", message.getClass().getSimpleName())
            .build()
        );
    }
}
