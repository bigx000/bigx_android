package com.bigx.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zhaoshuai on 16/3/18.
 */
public class JsonUtil {
    
    private static Gson mGson = new Gson();

    /**
     * 将对象转换为json字符串
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String serialize(T object) {
        return mGson.toJson(object);
    }

    /**
     * 将json字符串转换为对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json, Class<T> clazz) {
        return mGson.fromJson(json, clazz);
    }

    /**
     * 将json对象转换为实体对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deserialize(JsonObject json, Class<T> clazz) {
        return mGson.fromJson(json, clazz);
    }

    /**
     * 将json字符串转换为对象
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json, Type type) {
        return mGson.fromJson(json, type);
    }

    /**
     * 将json字符串转换为list
     * @param json
     * @param token
     * @param <T>
     * @return
     */
    public static <T> List<T> deserializeList(String json, TypeToken<List<T>> token) {
        return mGson.fromJson(json, token.getType());
    }
}
