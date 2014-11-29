package com.apartment.management.utils;

import com.apartment.management.dto.CommunityDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

	public static String parseObjectToJson(final Object obj) {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(obj);

	}

	public static <T> T parseJsonToObject(final String json, Class<T> clazz) {
		System.out.println(json);
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, clazz);
	}
	public static void main(String[] args) {
		String payload = "{\"name\":\"sdfsdf\",\"type\":\"Apartments\",\"address1\":\"sdfsdf\",\"address2\":\"sdfsdf\",\"city\":\"Bengalore\",\"address3\":\"sdfsdf\",\"postalCode\":\"234234\",\"state\":\"Andhra Pradesh\",\"country\":\"India\",\"description\":\"sdfsdfsdf\"}";
		CommunityDTO dto = parseJsonToObject(payload,CommunityDTO.class);
		System.out.println(dto.getName());
		
	}

}