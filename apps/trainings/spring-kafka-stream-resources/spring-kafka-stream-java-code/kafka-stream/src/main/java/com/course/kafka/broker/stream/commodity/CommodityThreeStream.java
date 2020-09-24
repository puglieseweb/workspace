package com.course.kafka.broker.stream.commodity;

import static com.course.kafka.util.CommodityStreamUtil.generateStorageKey;
import static com.course.kafka.util.CommodityStreamUtil.isCheap;
import static com.course.kafka.util.CommodityStreamUtil.isLargeQuantity;
import static com.course.kafka.util.CommodityStreamUtil.isPlastic;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaStreamBrancher;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.course.kafka.broker.message.OrderMessage;
import com.course.kafka.broker.message.OrderPatternMessage;
import com.course.kafka.broker.message.OrderRewardMessage;
import com.course.kafka.util.CommodityStreamUtil;

//@Configuration
public class CommodityThreeStream {

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
		final var branchProducer = Produced.with(stringSerde, orderPatternSerde);
		new KafkaStreamBrancher<String, OrderPatternMessage>()
				.branch(isPlastic(), kstream -> kstream.to("t.commodity.pattern-three.plastic", branchProducer))
				.defaultBranch(kstream -> kstream.to("t.commodity.pattern-three.notplastic", branchProducer))
				.onTopOf(maskedOrderStream.mapValues(CommodityStreamUtil::mapToOrderPattern));

		// 2nd sink stream to reward
		KStream<String, OrderRewardMessage> rewardStream = maskedOrderStream.filter(isLargeQuantity())
				.filterNot(isCheap()).mapValues(CommodityStreamUtil::mapToOrderReward);
		rewardStream.to("t.commodity.reward-three", Produced.with(stringSerde, orderRewardSerde));

		// 3rd sink stream to storage
		KStream<String, OrderMessage> storageStream = maskedOrderStream.selectKey(generateStorageKey());
		storageStream.to("t.commodity.storage-three", Produced.with(stringSerde, orderSerde));

		return maskedOrderStream;
	}

}
