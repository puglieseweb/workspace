package com.puglieseweb.app.sample.kafka_stream.broker.stream.commodity;

import static com.puglieseweb.app.sample.kafka_stream.util.CommodityStreamUtil.generateStorageKey;
import static com.puglieseweb.app.sample.kafka_stream.util.CommodityStreamUtil.isCheap;
import static com.puglieseweb.app.sample.kafka_stream.util.CommodityStreamUtil.isLargeQuantity;
import static com.puglieseweb.app.sample.kafka_stream.util.CommodityStreamUtil.isPlastic;

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
public class CommodityTwoStream {

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
		@SuppressWarnings("unchecked")
		KStream<String, OrderPatternMessage>[] patternStream = maskedOrderStream
				.mapValues(CommodityStreamUtil::mapToOrderPattern).branch(isPlastic(), (k, v) -> true);

		int plasticIndex = 0;
		int notPlasticIndex = 1;

		patternStream[plasticIndex].to("t.commodity.pattern-two.plastic",
				Produced.with(stringSerde, orderPatternSerde));
		patternStream[notPlasticIndex].to("t.commodity.pattern-two.notplastic",
				Produced.with(stringSerde, orderPatternSerde));

		// 2nd sink stream to reward
		KStream<String, OrderRewardMessage> rewardStream = maskedOrderStream.filter(isLargeQuantity())
				.filterNot(isCheap()).mapValues(CommodityStreamUtil::mapToOrderReward);
		rewardStream.to("t.commodity.reward-two", Produced.with(stringSerde, orderRewardSerde));

		// 3rd sink stream to storage
		KStream<String, OrderMessage> storageStream = maskedOrderStream.selectKey(generateStorageKey());
		storageStream.to("t.commodity.storage-two", Produced.with(stringSerde, orderSerde));

		return maskedOrderStream;
	}

}
