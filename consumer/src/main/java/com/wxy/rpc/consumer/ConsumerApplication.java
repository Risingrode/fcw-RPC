package com.wxy.rpc.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @Author: fcw
 * @Description: 先启动consumer，再启动provider
 * @Date: 2023-11-23   17:49
 */

@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
