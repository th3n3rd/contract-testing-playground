package com.example.orders;

import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
class MessagingRouter implements MessageRoutingCallback {

    private static final String Discarded = "discarded";
    private static final List<String> supportedTypes = List.of(
        CheckoutStarted.class.getSimpleName()
    );

    @Override
    public FunctionRoutingResult routingResult(Message<?> message) {
        var type = (String) message.getHeaders().get("type");
        return new FunctionRoutingResult(resolveBinding(type));
    }

    private boolean supports(String type) {
        return Objects.nonNull(type) && supportedTypes.contains(type);
    }

    private String resolveBinding(String messageType) {
        if (!supports(messageType)) {
            return Discarded;
        }
        return Character.toLowerCase(messageType.charAt(0)) + messageType.substring(1);
    }
}

