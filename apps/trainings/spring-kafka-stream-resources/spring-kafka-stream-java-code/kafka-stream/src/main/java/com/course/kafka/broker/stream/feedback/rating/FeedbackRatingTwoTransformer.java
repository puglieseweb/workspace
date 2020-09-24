package com.course.kafka.broker.stream.feedback.rating;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import com.course.kafka.broker.message.FeedbackMessage;
import com.course.kafka.broker.message.FeedbackRatingTwoMessage;

public class FeedbackRatingTwoTransformer implements ValueTransformer<FeedbackMessage, FeedbackRatingTwoMessage> {

	private ProcessorContext processorContext;
	private final String stateStoreName;
	private KeyValueStore<String, FeedbackRatingTwoStoreValue> ratingStateStore;

	public FeedbackRatingTwoTransformer(String stateStoreName) {
		if (StringUtils.isEmpty(stateStoreName)) {
			throw new IllegalArgumentException("State store name must not empty");
		}

		this.stateStoreName = stateStoreName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(ProcessorContext context) {
		this.processorContext = context;
		this.ratingStateStore = (KeyValueStore<String, FeedbackRatingTwoStoreValue>) this.processorContext
				.getStateStore(this.stateStoreName);
	}

	@Override
	public FeedbackRatingTwoMessage transform(FeedbackMessage feedbackMessage) {
		var storeValue = Optional.ofNullable(ratingStateStore.get(feedbackMessage.getLocation()))
				.orElse(new FeedbackRatingTwoStoreValue());
		var ratingMap = storeValue.getRatingMap();

		var currentRatingCount = Optional.ofNullable(ratingMap.get(feedbackMessage.getRating())).orElse(0l);
		var newRatingCount = currentRatingCount + 1;
		ratingMap.put(feedbackMessage.getRating(), newRatingCount);
		ratingStateStore.put(feedbackMessage.getLocation(), storeValue);

		// send this message to sink topic
		var branchRating = new FeedbackRatingTwoMessage();
		branchRating.setLocation(feedbackMessage.getLocation());
		branchRating.setRatingMap(ratingMap);
		branchRating.setAverageRating(calculateAverage(ratingMap));

		return branchRating;
	}

	private double calculateAverage(Map<Integer, Long> ratingMap) {
		var sumRating = 0l;
		var countRating = 0;

		for (var entry : ratingMap.entrySet()) {
			sumRating += entry.getKey() * entry.getValue();
			countRating += entry.getValue();
		}

		return Math.round((double) sumRating / countRating * 10d) / 10d;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
