package com.puglieseweb.core.services.kafka.subscriber.handler;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@CommonsLog
@Component
public class ListenerErrorHandler implements ConsumerAwareListenerErrorHandler {
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        log.warn("Error processing message {} with exception  ");
        return null;
    }
}
