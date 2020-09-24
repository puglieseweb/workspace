package com.puglieseweb.core.services.kafka.subscriber.handler;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ConsumerAwareErrorHandler;

@CommonsLog
public class GlobalListenerErrorHandler implements ConsumerAwareErrorHandler {
    @Override
    public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
        log.warn("Error processing message {} with exception  ");

    }
}
