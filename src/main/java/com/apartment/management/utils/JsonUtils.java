package com.apartment.management.utils;

import java.lang.reflect.Type;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.apartment.management.dto.AppointmentDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonUtils {

  public static String parseObjectToJson(final Object obj) {
    try {
      GsonBuilder builder = new GsonBuilder();
      builder.registerTypeAdapter(DateTime.class, new DateTimeTypeConverter());
      Gson gson = builder.create();
      return gson.toJson(obj);
    } catch (Exception e) {
      throw new RuntimeException("unable to parse object to json::", e);
    }

  }

  public static <T> T parseJsonToObject(final String json, Class<T> clazz) {
    try {
      GsonBuilder builder = new GsonBuilder();
      builder.registerTypeAdapter(DateTime.class, new DateTimeTypeConverter());
      Gson gson = builder.create();
      return gson.fromJson(json, clazz);
    } catch (Exception e) {
      throw new RuntimeException("unable to parse json to object::" + json, e);
    }
  }

 private static class DateTimeTypeConverter
  implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
  @Override
  public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    try {
      return new DateTime(json.getAsString());
    } catch (IllegalArgumentException e) {
      // May be it came in formatted as a java.util.Date, so try that
      Date date = context.deserialize(json, Date.class);
      return new DateTime(date);
    }
    }

  @Override
  public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.toString(DateTimeZone.UTC.toString()));
  }
}
  public static void main(String[] args) {
    String payload =
      "{\"country\":\"India\",\"address1\":\"kphb phase9\",\"city\":\"vizag\",\"postalCode\":500054,\"state\":\"Andhra Pradesh\",\"name\":\"top building\",\"totalUnits\":\"11\",\"totalFloors\":\"11\"}";
   // BuildingDTO dto = parseJsonToObject(payload, BuildingDTO.class);
  //  System.out.println(dto.getState());
    String appointmentPayload =
      "{\"appointmentDate\":\"2015-01-26T18:30:00.000Z\",\"remindMe\":\"2015-01-27T18:30:00.000Z\",\"description\":\"sdfsdf\",\"contactDTO\":{\"id\":\"CN68137574\",\"emailId\":\"rama.guntu@gmail.com\",\"phoneNumber\":\"1111111111\",\"type\":\"Maintenance\",\"name\":\"Rama\",\"address\":{\"address1\":\"ews 1032 road no 2 kphb colony hyderabad\",\"address2\":\"sdf\",\"address3\":\"sdf\",\"country\":\"India\",\"city\":\"hyderabad\",\"state\":\"Andhra Pradesh\",\"postalCode\":222222},\"flatId\":\"F68193907\",\"userId\":\"myadmin@gmail.com\"}}";
    try {
      GsonBuilder builder = new GsonBuilder();
      builder.registerTypeAdapter(DateTime.class, new DateTimeTypeConverter());
      Gson gson = builder.create();
      AppointmentDTO dto = gson.fromJson(appointmentPayload, AppointmentDTO.class);
      System.out.println(dto.getAppointmentDate());
    } catch (Exception e) {
      throw new RuntimeException("unable to parse json to object::", e);
    }
  }

}
