package com.puglieseweb.core.services.kafka.subscriber;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class KafkaSubscriber {

    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaSubscriber.class);

    @Autowired
    private KafkaTemplate<String, String> template;

    private final CountDownLatch latch = new CountDownLatch(3);

    @Value("${spring.kafka.topic}")
    private String topics;


    @KafkaListener(topics = "#{'${spring.kafka.topic}'.split(',')}",
            groupId = "#{'${spring.kafka.group-id}'}",
            containerFactory = "farLocationContainerFactory",
            errorHandler = "listenerErrorHandler"
    )
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        LOGGER.info("kafka topic {} has massage {}", topics, cr.toString());
    }
}