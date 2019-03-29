package com.pinghua.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
public class JsonUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	private JsonUtil() {
	    throw new IllegalAccessError("Utility class");
	  }

	/**
	 * 对象序列化
	 * @param value
	 * @return
	 */
	public static String objectToJsonString(Object value) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 反序列化
	 * @param json
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T parseObj(String json, Class<T> clazz) {
		T t = null;
		try {
			t = objectMapper.readValue(json.getBytes(), clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * map转换成json字符串
	 * @return
	 */
//	public static String mapToJsonString(Map<String, String> map) {
//		return JSONArray.fromObject(map).toString();
//	}

}
