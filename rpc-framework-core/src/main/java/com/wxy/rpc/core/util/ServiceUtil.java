package com.wxy.rpc.core.util;

import com.google.gson.Gson;
import com.wxy.rpc.core.common.ServiceInfo;

import java.util.Collections;
import java.util.Map;

/**
 *
 * @Author: fcw
 * @Description: 使用Gson将ServiceInfo对象转换为Map对象，或者将Map对象转换为ServiceInfo对象
 * @Date: 2023-12-02   16:25
 */

public class ServiceUtil {
    // Gson 对象 用于序列化和反序列化
    public static final Gson gson = new Gson();


    /**
     * 根据 服务名称 + 版本号 生成注册服务的 key
     *
     * @param serverName 服务名
     * @param version    版本号
     * @return 生成最终的服务名称: serverName-version
     */
    public static String serviceKey(String serverName, String version) {

        return String.join("-", serverName, version);
    }

    /**
     * 将 serviceInfo 对象转换为 map
     *
     * @param serviceInfo serviceInfo实列
     * @return Map
     */
    // 抑制编译器产生关于未经检查的操作的警告
    // rawtypes 是忽略与原始类型相关的警告信息
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map toMap(ServiceInfo serviceInfo) {
        if (serviceInfo == null) {
            return Collections.emptyMap();
        }
        // gson.toJson(serviceInfo): 将 serviceInfo 对象转换为 JSON 格式的字符串。
        // gson.fromJson(..., Map.class): 将上一步得到的 JSON 字符串转换为 Map 对象。
        Map map = gson.fromJson(gson.toJson(serviceInfo), Map.class);
        map.put("port", serviceInfo.getPort().toString());
        return map;
    }

    /**
     * 将 map 转换为 serviceInfo 实例
     *
     * @param map Map 实例
     * @return serviceInfo 实例
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static ServiceInfo toServiceInfo(Map map) {
        // 将 map 中的 port 从 String 类型转换为 Integer 类型
        map.put("port", Integer.parseInt(map.getOrDefault("port", "0").toString()));
        // 将 map 转换为 JSON 格式的字符串，再将 JSON 字符串转换为 ServiceInfo 对象
        return gson.fromJson(gson.toJson(map), ServiceInfo.class);
    }
}
