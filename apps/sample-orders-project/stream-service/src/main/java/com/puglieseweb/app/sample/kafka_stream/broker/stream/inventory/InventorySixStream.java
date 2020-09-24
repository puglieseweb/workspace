package com.puglieseweb.app.sample.kafka_stream.broker.stream.inventory;

import java.time.Duration;

import com.puglieseweb.app.sample.kafka_stream.broker.message.InventoryMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.WindowedSerdes;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.puglieseweb.app.sample.kafka_stream.util.InventoryTimestampExtractor;

//@Configuration
public class InventorySixStream {

	@Bean
	public KStream<String, InventoryMessage> kstreamInventory(StreamsBuilder builder) {
		var stringSerde = Serdes.String();
		var inventorySerde = new JsonSerde<>(InventoryMessage.class);
		var longSerde = Serdes.Long();

		var windowLength = Duration.ofHours(1);
		var hopLength = Duration.ofMinutes(20);
		var windowSerde = WindowedSerdes.timeWindowedSerdeFrom(String.class, windowLength.toMillis());

		var inventoryTimestampExtractor = new InventoryTimestampExtractor();

		var inventoryStream = builder.stream("t.commodity.inventory",
				Consumed.with(stringSerde, inventorySerde, inventoryTimestampExtractor, null));

		inventoryStream
				.mapValues((k, v) -> v.getType().equalsIgnoreCase("ADD") ? v.getQuantity() : -1 * v.getQuantity())
				.groupByKey().windowedBy(TimeWindows.of(windowLength).advanceBy(hopLength))
				.reduce(Long::sum, Materialized.with(stringSerde, longSerde)).toStream()
				.through("t.commodity.inventory-total-six", Produced.with(windowSerde, longSerde))
				.print(Printed.toSysOut());

		return inventoryStream;
	}

}
