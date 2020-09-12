package com.puglieseweb.app.billswallet.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

//    private static final String TOPIC = "users";
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    public void sendRegistration(UUID custerId, UUID businessId) {
//
//
//        public void sendMessage (String message){
//            logger.info(String.format("#### -> Producing message -> %s", message));
////        this.kafkaTemplate.send(TOPIC, message);
//        }
//    }
}
