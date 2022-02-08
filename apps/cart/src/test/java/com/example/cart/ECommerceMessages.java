package com.example.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Import(TestChannelBinderConfiguration.class)
@Service
class ECommerceMessages {

    private final static ObjectMapper mapper = new ObjectMapper();
    private final OutputDestination output;

    ECommerceMessages(OutputDestination output) {
        this.output = output;
    }

    Message<byte[]> takeMessage() {
        return output.receive(5000, "ecommerce-messages");
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
