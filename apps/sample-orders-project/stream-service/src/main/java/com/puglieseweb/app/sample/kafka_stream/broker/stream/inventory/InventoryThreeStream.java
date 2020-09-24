package com.puglieseweb.app.sample.kafka_stream.broker.stream.inventory;

import com.puglieseweb.app.sample.kafka_stream.broker.message.InventoryMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class InventoryThreeStream {

	@Bean
	public KStream<String, InventoryMessage> kstreamInventory(StreamsBuilder builder) {
		var stringSerde = Serdes.String();
		var inventorySerde = new JsonSerde<>(InventoryMessage.class);
		var longSerde = Serdes.Long();

		var inventoryStream = builder.stream("t.commodity.inventory", Consumed.with(stringSerde, inventorySerde));

		inventoryStream
				.mapValues((k, v) -> v.getType().equalsIgnoreCase("ADD") ? v.getQuantity() : -1 * v.getQuantity())
				.groupByKey().reduce(Long::sum, Materialized.with(stringSerde, longSerde)).toStream()
				.to("t.commodity.inventory-total-three", Produced.with(stringSerde, longSerde));

		return inventoryStream;
	}

}
