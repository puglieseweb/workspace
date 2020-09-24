package com.puglieseweb.core.services.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Service
public class KafkaSubscriber {

    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaSubscriber.class);

    @Autowired
    private KafkaTemplate<String, String> template;

    private final CountDownLatch latch = new CountDownLatch(3);

    @Value("${spring.kafka.topic}")
    private String topics;


    @KafkaListener(topics = "#{'${spring.kafka.topic}'.split(',')}", groupId = "group_id1")
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        LOGGER.info("kafka topic {} has massage {}", topics, cr.toString());
    }
}