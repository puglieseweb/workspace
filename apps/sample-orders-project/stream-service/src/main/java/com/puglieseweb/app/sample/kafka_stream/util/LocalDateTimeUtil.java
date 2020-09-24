package com.puglieseweb.app.sample.kafka_stream.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeUtil {

	public static long toEpochTimestamp(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

}
