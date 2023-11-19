package com.wxy.rpc.core.other;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wxy.rpc.core.common.ServiceInfo;

import java.util.Collections;
import java.util.Map;

public class MapTest {

    private static final Gson gson = new GsonBuilder().create();

    public static Map<String, Object> toMap(ServiceInfo serviceInfo) {
        if (serviceInfo == null) {
            return Collections.emptyMap();
        }
        Map map = gson.fromJson(gson.toJson(serviceInfo), Map.class);
        map.put("port", serviceInfo.getPort().toString());
        return map;
    }

    public static void main(String[] args) {
        // 创建 ServiceInfo 对象
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceName("exampleService");
        serviceInfo.setAddress("localhost");
        serviceInfo.setPort(8080);

        // 使用 toMap 方法将 ServiceInfo 转换为 Map
        Map<String, Object> map = toMap(serviceInfo);

        // 打印结果
        System.out.println(map);
        // 运行结果： {serviceName=exampleService, address=localhost, port=8080}
    }

}
