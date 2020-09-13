package com.puglieseweb.core.services.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class KafkaProducer {

    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> template;


    @Value("${spring.kafka.topic}")
    private String topics;


    @PostConstruct
    public void produce() {
        this.template.send(topics, "foo1");
        this.template.send(topics, "foo2");
        this.template.send(topics, "foo3");
        LOGGER.info("All sent");
    }
}