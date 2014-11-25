package com.apartment.management.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

	public static String convertJavaObjectToJson(final Object obj) {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(obj);

	}

	public static <T> T parseJsonToObject(final String json, Class<T> clazz) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, clazz);
	}

}