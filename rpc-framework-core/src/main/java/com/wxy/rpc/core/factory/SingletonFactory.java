package com.wxy.rpc.core.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取单实例对象的工厂
 *
 * @author Wuxy
 * @version 1.0
 * @ClassName SingletonFactory
 * @Date 2023/1/6 19:16
 */
public final class SingletonFactory {
    private static final Map<String, Object> OBJECT_MAP = new ConcurrentHashMap<>();

    // 拿到实例
    public static <T> T getInstance(Class<T> clazz) {
        try {
            String name = clazz.getName();
            if (OBJECT_MAP.containsKey(name)) {
                // cast 的作用是将一个对象转换为指定类型
                return clazz.cast(OBJECT_MAP.get(name));
            } else {
                // 创建新实例，并且加入缓存之中
                T instance = clazz.getDeclaredConstructor().newInstance();
                OBJECT_MAP.put(name, instance);
                return instance;
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
