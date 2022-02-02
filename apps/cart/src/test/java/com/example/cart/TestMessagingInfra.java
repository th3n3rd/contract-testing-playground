package com.example.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Import(TestChannelBinderConfiguration.class)
@Service
class TestMessagingInfra {

    private final static ObjectMapper mapper = new ObjectMapper();
    private final OutputDestination output;

    TestMessagingInfra(OutputDestination output) {
        this.output = output;
    }

    Message<byte[]> takeMessage() {
        return output.receive(5000, MessagingChannels.Out);
    }

    @SneakyThrows
    <T> T takeMessage(Class<T> classType) {
        return mapper.readValue(
            takeMessage().getPayload(),
            classType
        );
    }

    void clear() {
        output.clear();
    }
}
