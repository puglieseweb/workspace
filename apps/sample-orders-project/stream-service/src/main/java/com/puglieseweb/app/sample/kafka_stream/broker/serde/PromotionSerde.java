package com.puglieseweb.app.sample.kafka_stream.broker.serde;

import com.puglieseweb.app.sample.kafka_stream.broker.message.PromotionMessage;

public class PromotionSerde extends CustomJsonSerde<PromotionMessage> {

	public PromotionSerde() {
		super(new CustomJsonSerializer<PromotionMessage>(),
				new CustomJsonDeserializer<PromotionMessage>(PromotionMessage.class));
	}

}
