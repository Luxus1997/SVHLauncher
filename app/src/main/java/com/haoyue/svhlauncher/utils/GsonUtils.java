package com.haoyue.svhlauncher.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonUtils {

    public GsonUtils() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param <T>
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T getPerson(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
        }
        return t;
    }

    /**
     * @param <T>
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> getPersons(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
//        try {
//            Gson gson = new Gson();
//            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
//            }.getType());
//        } catch (Exception e) {
//        }
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jsonarray = parser.parse(jsonString).getAsJsonArray();
            for (JsonElement element : jsonarray
            ) {
                list.add(gson.fromJson(element, cls));
            }
        } catch (Exception e) {

        }

        return list;
    }

    /**
     * @param jsonString
     * @return
     */
    public static List<String> getList(String jsonString) {
        List<String> list = new ArrayList<String>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {

        }
        return list;
    }

    public static String toJson(Object src) {
        try {
            Gson gson = new Gson();
            return gson.toJson(src);
        } catch (Exception e) {
            return null;
        }
    }


    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString,
                    new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }
}