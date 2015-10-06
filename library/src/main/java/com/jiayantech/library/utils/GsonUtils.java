package com.jiayantech.library.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by 健兴 on 2015/8/2.
 */
public class GsonUtils {
    private static Gson sGson;

    public static Gson build() {
        if (sGson == null) {
            synchronized (GsonUtils.class) {
                if (sGson == null) {
                    GsonBuilder gb = new GsonBuilder();
                    gb.registerTypeAdapter(String.class, new StringConverter()).
                            registerTypeAdapter(double.class, new DoubleConverter()).
                            registerTypeAdapter(int.class, new IntegerConverter()).
                            registerTypeAdapter(boolean.class, new BooleanConverter()).
                            registerTypeAdapter(long.class, new LongConverter());
                    sGson = gb.create();
                }
            }
        }
        return sGson;
    }
}

class StringConverter implements JsonSerializer<String>, JsonDeserializer<String> {
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src == null ? "" : src);
    }

    //    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
//            throws JsonParseException {
//
//        return json.getAsJsonPrimitive().getAsString();
//    }
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        //作容错
//        String result = "";
//        try {
//            result = json.getAsJsonPrimitive().getAsString();
//        }catch (JsonParseException e){
//
//            e.printStackTrace();
//        }catch (IllegalStateException e){
//            e.printStackTrace();
//        }
//        return result;
        return json.isJsonPrimitive() ? json.getAsJsonPrimitive().getAsString() : json.toString();
    }
}

class DoubleConverter implements JsonSerializer<Double>, JsonDeserializer<Double> {
    @Override
    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src == null ? 0 : src);
    }

    @Override
    public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsJsonPrimitive().getAsDouble();
    }
}

class IntegerConverter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
    @Override
    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src == null ? 0 : src);
    }

    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsJsonPrimitive().getAsInt();
    }
}

class BooleanConverter implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {
    @Override
    public JsonElement serialize(Boolean src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src == null ? false : src);
    }

    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsJsonPrimitive().getAsBoolean();
    }

}

class LongConverter implements JsonDeserializer<Long>, JsonSerializer<Long> {

    @Override
    public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String jsonStr = json.getAsString();
        if (jsonStr.endsWith("L") || jsonStr.endsWith("l")) {
            jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
            return Long.valueOf(jsonStr);
        }else{
            return json.getAsJsonPrimitive().getAsLong();
        }
    }

    @Override
    public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src == null ? -1 : src);
    }
}