package com.example.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Import(TestChannelBinderConfiguration.class)
@Service
class EcommerceMessages {

    private final static String Destination = "ecommerce-messages";
    private final static ObjectMapper mapper = new ObjectMapper();

    private final InputDestination input;
    private final OutputDestination output;
    private final List<UUID> sentMessages;

    EcommerceMessages(InputDestination input, OutputDestination output) {
        this.input = input;
        this.output = output;
        this.sentMessages = new ArrayList<>();
    }

    void putMessage(Object payload, String messageType) {
        var message = MessageBuilder
            .withPayload(payload)
            .setHeader("type", messageType).build();
        input.send(
            message,
            Destination
        );
        sentMessages.add(message.getHeaders().getId());
    }

    void putMessage(Object payload) {
        putMessage(payload, payload.getClass().getSimpleName());
    }

    Message<byte[]> takeMessage() {
        UUID messageId;
        Message<byte[]> message;
        do {
            message = output.receive(5000, Destination);
            if (Objects.isNull(message)) {
                break;
            }
            messageId = message.getHeaders().getId();
        } while (sentMessages.contains(messageId));

        return message;
    }

    @SneakyThrows
    <T> T takeMessage(Class<T> classType) {
        var message = takeMessage();
        if (Objects.isNull(message)) {
            throw new IllegalStateException("No message received");
        }
        return mapper.readValue(
            message.getPayload(),
            classType
        );
    }

    void clear() {
        output.clear();
    }
}
