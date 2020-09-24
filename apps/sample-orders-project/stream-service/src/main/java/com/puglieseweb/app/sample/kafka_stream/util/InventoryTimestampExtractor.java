package com.puglieseweb.app.sample.kafka_stream.util;

import com.puglieseweb.app.sample.kafka_stream.broker.message.InventoryMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class InventoryTimestampExtractor implements TimestampExtractor {

	@Override
	public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
		var inventoryMessage = (InventoryMessage) record.value();

		return inventoryMessage != null ? LocalDateTimeUtil.toEpochTimestamp(inventoryMessage.getTransactionTime())
				: record.timestamp();
	}

}
