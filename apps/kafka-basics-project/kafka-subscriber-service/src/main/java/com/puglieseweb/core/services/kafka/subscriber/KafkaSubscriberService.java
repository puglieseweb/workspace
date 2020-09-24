package com.puglieseweb.core.services.kafka.subscriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class KafkaSubscriberService {

	public static void main(String[] args) {
		SpringApplication.run(KafkaSubscriberService.class, args);
	}

}
