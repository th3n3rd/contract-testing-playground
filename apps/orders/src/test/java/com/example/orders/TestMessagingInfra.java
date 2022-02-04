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

@Import(TestChannelBinderConfiguration.class)
@Service
class TestMessagingInfra {

    private final static ObjectMapper mapper = new ObjectMapper();

    private final InputDestination input;
    private final OutputDestination output;

    TestMessagingInfra(InputDestination input, OutputDestination output) {
        this.input = input;
        this.output = output;
    }

    void putMessage(Object payload) {
        input.send(MessageBuilder.withPayload(payload).build()); // TODO: what should be the right destination name?
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
