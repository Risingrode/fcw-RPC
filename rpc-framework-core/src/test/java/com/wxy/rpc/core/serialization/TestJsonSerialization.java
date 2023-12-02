package com.wxy.rpc.core.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wxy.rpc.core.common.ServiceInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

/**
 * @author Wuxy
 * @version 1.0
 * @ClassName TestJsonSerialization
 * @Date 2023/1/6 16:03
 */
public class TestJsonSerialization {

    public static void main(String[] args) {
        Gson gson = new Gson();
//        String hello = "hello";
//
//        // 序列化对象
//        String toJson = gson.toJson(hello);
//        System.out.println(toJson);
//
//        // 反序列化对象
//        String json = gson.fromJson(toJson, String.class);
//        System.out.println(json);
//
//        gson = new GsonBuilder().disableInnerClassSerialization().create();
//        // 序列化 Java Class
//        String json1 = gson.toJson(String.class);
//        System.out.println(json1);



        // 创建一个 ServiceInfo 对象
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setAppName("TestApp");
        serviceInfo.setServiceName("TestService");
        serviceInfo.setVersion("1.0");
        serviceInfo.setAddress("127.0.0.1");
        serviceInfo.setPort(8080);

        // 将 ServiceInfo 对象转换为 JSON 字符串，然后将这个字符串转换为 Map 对象
        Map map = gson.fromJson(gson.toJson(serviceInfo), Map.class);

        // 打印转换后的 Map 对象
        System.out.println(map);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String username;

        private Date date;
    }
}
