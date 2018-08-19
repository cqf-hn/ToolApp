package com.cqf.hn.tool.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Type;
import java.math.BigDecimal;


/**
 * Created by Admin on 2018/3/31.
 */
public class GsonUtil {

    private static Gson gson;
    /**
     * 自定义TypeAdapter ,null对象将被解析成空字符串
     */
    private static final TypeAdapter<String> STRING = new TypeAdapter<String>() {
        public String read(JsonReader reader) {
            try {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return "";//原先是返回Null，这里改为返回空字符串
                }
                return reader.nextString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public void write(JsonWriter writer, String value) {
            try {
                if (value == null) {
                    writer.nullValue();
                    return;
                }
                writer.value(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private static JsonSerializer<Double> DOUBLE = new JsonSerializer<Double>() {
        @Override
        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
            if (src.isNaN() || src.isInfinite())
                return new JsonPrimitive(src.toString());
            if (src == src.longValue())
                return new JsonPrimitive(src.longValue());
            return new JsonPrimitive(src);
        }
    };

    private static final JsonSerializer<BigDecimal> BIGDECIMAL = new JsonSerializer<BigDecimal>() {
        @Override
        public JsonElement serialize(BigDecimal src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    };

    static {
        GsonBuilder gsonBulder = new GsonBuilder();
        gsonBulder.registerTypeAdapter(String.class, STRING);//所有String类型null替换为字符串“”
        gsonBulder.registerTypeAdapter(Double.class, DOUBLE);
        gsonBulder.registerTypeAdapter(BigDecimal.class, BIGDECIMAL);
        gson = gsonBulder.create();
    }

    /**
     * Json字符串 转为指定对象
     *
     * @param json json字符串
     * @param type 对象类型
     * @param <T>  对象类型
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T toBean(String json, Class<T> type) {
        try {
            return gson.fromJson(json, type);
        } catch (RuntimeException e) {
            LogUtils.d("json".concat(json).concat(" Exception ").concat(e.getMessage()));
            return null;
        }
    }

    public static String toGsonString(Object o) {
        try {
            return gson.toJson(o);
        } catch (RuntimeException e) {
            LogUtils.d("json".concat(o.toString()).concat(" Exception ").concat(e.getMessage()));
            return "";
        }
    }

    public static Gson getGson() {
        return gson;
    }
}
