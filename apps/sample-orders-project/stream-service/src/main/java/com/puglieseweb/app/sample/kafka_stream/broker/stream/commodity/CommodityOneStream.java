package com.puglieseweb.app.sample.kafka_stream.broker.stream.commodity;

import static com.puglieseweb.app.sample.kafka_stream.util.CommodityStreamUtil.isLargeQuantity;

import com.puglieseweb.app.sample.kafka_stream.broker.message.OrderMessage;
import com.puglieseweb.app.sample.kafka_stream.broker.message.OrderPatternMessage;
import com.puglieseweb.app.sample.kafka_stream.broker.message.OrderRewardMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.puglieseweb.app.sample.kafka_stream.util.CommodityStreamUtil;

//@Configuration
public class CommodityOneStream {

	@Bean
	public KStream<String, OrderMessage> kstreamCommodityTrading(StreamsBuilder builder) {
		var stringSerde = Serdes.String();
		var orderSerde = new JsonSerde<>(OrderMessage.class);
		var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
		var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

		KStream<String, OrderMessage> maskedOrderStream = builder
				.stream("t.commodity.order", Consumed.with(stringSerde, orderSerde))
				.mapValues(CommodityStreamUtil::maskCreditCard);

		// 1st sink stream to pattern
		// summarize order item (total = price * quantity)
		KStream<String, OrderPatternMessage> patternStream = maskedOrderStream
				.mapValues(CommodityStreamUtil::mapToOrderPattern);
		patternStream.to("t.commodity.pattern-one", Produced.with(stringSerde, orderPatternSerde));

		// 2nd sink stream to reward
		// filter only "large" quantity
		KStream<String, OrderRewardMessage> rewardStream = maskedOrderStream.filter(isLargeQuantity())
				.mapValues(CommodityStreamUtil::mapToOrderReward);
		rewardStream.to("t.commodity.reward-one", Produced.with(stringSerde, orderRewardSerde));

		// 3rd sink stream to storage
		// no transformation
		maskedOrderStream.to("t.commodity.storage-one", Produced.with(stringSerde, orderSerde));

		return maskedOrderStream;
	}

}
