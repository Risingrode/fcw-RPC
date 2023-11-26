package com.wxy.api;


import java.io.*;
/**
 *
 * @Author: fcw
 * @Description: 测试字节流
 * @Date: 2023-11-25   22:55
 */

public class SerializableTest {
    public static void main(String[] args) {
        // 创建一个 User 对象
        User user = new User("john_doe", "password123", 25);

        // 将 User 对象序列化为字节流
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream)) {

            outputStream.writeObject(user);

            // 获取序列化后的字节数组
            byte[] serializedUser = byteArrayOutputStream.toByteArray();

            // 打印字节流的内容
            for (byte b : serializedUser) {
                System.out.print(b + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
