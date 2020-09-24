package com.course.kafka.broker.serde;

import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomJsonSerializer<T> implements Serializer<T> {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, T data) {
		try {
			return objectMapper.writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			throw new SerializationException(e);
		}
	}

}
