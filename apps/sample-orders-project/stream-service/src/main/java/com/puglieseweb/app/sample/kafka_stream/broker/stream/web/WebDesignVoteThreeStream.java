package com.puglieseweb.app.sample.kafka_stream.broker.stream.web;

import com.puglieseweb.app.sample.kafka_stream.broker.message.WebColorVoteMessage;
import com.puglieseweb.app.sample.kafka_stream.broker.message.WebDesignVoteMessage;
import com.puglieseweb.app.sample.kafka_stream.broker.message.WebLayoutVoteMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.puglieseweb.app.sample.kafka_stream.util.WebColorVoteTimestampExtractor;
import com.puglieseweb.app.sample.kafka_stream.util.WebLayoutVoteTimestampExtractor;

//@Configuration
public class WebDesignVoteThreeStream {

	@Bean
	public KStream<String, WebDesignVoteMessage> kstreamWebDesignVote(StreamsBuilder builder) {
		var stringSerde = Serdes.String();
		var colorSerde = new JsonSerde<>(WebColorVoteMessage.class);
		var layoutSerde = new JsonSerde<>(WebLayoutVoteMessage.class);
		var designSerde = new JsonSerde<>(WebDesignVoteMessage.class);

		// color
		builder.stream("t.commodity.web.vote-color",
				Consumed.with(stringSerde, colorSerde, new WebColorVoteTimestampExtractor(), null))
				.mapValues(v -> v.getColor()).to("t.commodity.web.vote-three-username-color");
		var colorTable = builder.table("t.commodity.web.vote-three-username-color",
				Consumed.with(stringSerde, stringSerde));

		// layout
		builder.stream("t.commodity.web.vote-layout",
				Consumed.with(stringSerde, layoutSerde, new WebLayoutVoteTimestampExtractor(), null))
				.mapValues(v -> v.getLayout()).to("t.commodity.web.vote-three-username-layout");
		var layoutTable = builder.table("t.commodity.web.vote-three-username-layout",
				Consumed.with(stringSerde, stringSerde));

		// join
		var joinTable = colorTable.outerJoin(layoutTable, this::voteJoiner,
				Materialized.with(stringSerde, designSerde));
		joinTable.toStream().to("t.commodity.web.vote-three-result");

		// vote result
		joinTable.groupBy((username, votedDesign) -> KeyValue.pair(votedDesign.getColor(), votedDesign.getColor()))
				.count().toStream().print(Printed.<String, Long>toSysOut().withLabel("Vote three - color"));

		joinTable.groupBy((username, votedDesign) -> KeyValue.pair(votedDesign.getLayout(), votedDesign.getLayout()))
				.count().toStream().print(Printed.<String, Long>toSysOut().withLabel("Vote three - layout"));

		return joinTable.toStream();
	}

	private WebDesignVoteMessage voteJoiner(String color, String layout) {
		var result = new WebDesignVoteMessage();

		result.setColor(color);
		result.setLayout(layout);

		return result;
	}

}
