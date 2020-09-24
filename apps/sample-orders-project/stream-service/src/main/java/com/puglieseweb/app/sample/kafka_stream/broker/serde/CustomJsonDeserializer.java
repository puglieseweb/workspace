package com.puglieseweb.app.sample.kafka_stream.broker.serde;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomJsonDeserializer<T> implements Deserializer<T> {

	private ObjectMapper objectMapper = new ObjectMapper();
	private final Class<T> deserializedClass;

	public CustomJsonDeserializer(Class<T> deserializedClass) {
		Objects.requireNonNull(deserializedClass, "Deserialized class must not null");
		this.deserializedClass = deserializedClass;
	}

	@Override
	public T deserialize(String topic, byte[] data) {
		try {
			return objectMapper.readValue(data, deserializedClass);
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}

}
