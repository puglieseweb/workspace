package com.puglieseweb.app.sample.kafka_stream.broker.stream.flashsale;

import java.time.LocalDateTime;

import com.puglieseweb.app.sample.kafka_stream.broker.message.FlashSaleVoteMessage;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import com.puglieseweb.app.sample.kafka_stream.util.LocalDateTimeUtil;

public class FlashSaleVoteTwoValueTransformer implements ValueTransformer<FlashSaleVoteMessage, FlashSaleVoteMessage> {

	private final long voteStartTime;
	private final long voteEndTime;
	private ProcessorContext processorContext;

	public FlashSaleVoteTwoValueTransformer(LocalDateTime voteStart, LocalDateTime voteEnd) {
		this.voteStartTime = LocalDateTimeUtil.toEpochTimestamp(voteStart);
		this.voteEndTime = LocalDateTimeUtil.toEpochTimestamp(voteEnd);
	}

	@Override
	public void init(ProcessorContext context) {
		this.processorContext = context;
	}

	@Override
	public FlashSaleVoteMessage transform(FlashSaleVoteMessage value) {
		var recordTime = processorContext.timestamp();

		return (recordTime >= voteStartTime && recordTime <= voteEndTime) ? value : null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
