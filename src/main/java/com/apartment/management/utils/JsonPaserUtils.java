package com.apartment.management.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonPaserUtils {
	
	public static String convertObjectToJson(final Object obj) {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(obj);
	}

}