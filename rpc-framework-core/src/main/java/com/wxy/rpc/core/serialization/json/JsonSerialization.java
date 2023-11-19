package com.wxy.rpc.core.serialization.json;

import com.google.gson.*;
import com.wxy.rpc.core.exception.SerializeException;
import com.wxy.rpc.core.serialization.Serialization;
import lombok.SneakyThrows;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * 基于 Gson 库实现的 JSON 序列化算法类
 *
 * @author Wuxy
 * @version 1.0
 * @ClassName JsonSerialization
 * @Date 2023/1/5 12:23
 */
public class JsonSerialization implements Serialization {
    // TODO　： 不太明白
    /**
     * 自定义 JavClass 对象序列化，解决 Gson 无法序列化 Class 信息
     */
    static class ClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {
        // 禁止抛出受检查异常
        @SneakyThrows
        @Override
        // json -> class
        public Class<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsString();
            return Class.forName(name);
        }

        @Override
        public JsonElement serialize(Class<?> src, Type typeOfSrc, JsonSerializationContext context) {
            // class -> json
            return new JsonPrimitive(src.getName());
        }
    }

    // 下面2个用于自定义的序列化和反序列化
    @Override
    public <T> byte[] serialize(T object) {
        try {
            // 注册类型适配器,创建gson实例
            Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCodec()).create();
            String json = gson.toJson(object);
            return json.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SerializeException("Json serialize failed.", e);
        }
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCodec()).create();
            String json = new String(bytes, StandardCharsets.UTF_8);
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            throw new SerializeException("Json deserialize failed.", e);
        }
    }
}
