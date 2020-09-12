package com.puglieseweb.app.billswallet.services.kafka;

import com.puglieseweb.app.billswallet.domain.Registration;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Arrays;
import java.util.UUID;

@EnableBinding(KStreamProcessor.class)
public class RegistrationProcess {

	@StreamListener("registration-requests")
	@SendTo("registration-processed")
	public KStream<?, Registration> process(KStream<?, String> input) {
		return input
				.flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
				.groupBy((key, value) -> value)
				.windowedBy(TimeWindows.of(5000))
				.count(Materialized.as("WordCounts-multi"))
				.toStream()
				.map((key, value) -> new KeyValue<>(null, new Registration(UUID.randomUUID(), UUID.randomUUID(), "", "")));
	}

}

interface KStreamProcessor {
	@Input("registration-requests")
	KStream<?, ?> input();

	@Output("registration-processed")
	KStream<?, ?> output1();
}