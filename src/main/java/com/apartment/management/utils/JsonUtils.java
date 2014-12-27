package com.apartment.management.utils;

import com.apartment.management.dto.BuildingDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

	public static String parseObjectToJson(final Object obj) {
		try{
		Gson gson = new GsonBuilder().create();
		return gson.toJson(obj);
		}catch(Exception e){
			throw new RuntimeException("unable to parse object to json::",e);
		}

	}

	public static <T> T parseJsonToObject(final String json, Class<T> clazz) {
		try{
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, clazz);
		}catch(Exception e){
			throw new RuntimeException("unable to parse json to object::"+json,e);
		}
	}

	public static void main(String[] args) {
		String payload = "{\"country\":\"India\",\"address1\":\"kphb phase9\",\"city\":\"vizag\",\"postalCode\":500054,\"state\":\"Andhra Pradesh\",\"name\":\"top building\",\"totalUnits\":\"11\",\"totalFloors\":\"11\"}";
		BuildingDTO dto = parseJsonToObject(payload,BuildingDTO.class);
		System.out.println(dto.getState());
		
	}

}