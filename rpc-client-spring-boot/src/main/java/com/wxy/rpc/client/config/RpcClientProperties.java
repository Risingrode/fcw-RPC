package com.wxy.rpc.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @Author: fcw
 * @Description: Rpc Client 配置属性类
 * @Date: 2023-11-25   23:47
 */

@Data
// 用于绑定配置文件中特定前缀的属性到 Java 类的注解
// TODO ： 这个注解不太明白
@ConfigurationProperties(prefix = "rpc.client")
public class RpcClientProperties {

    /**
     * Load balancing algorithm, candidate values include: (random, roundRobin, consistentHash), the default is random.
     */
    private String loadbalance;

    /**
     * Serialization algorithm, candidate values include: (JDK, JSON, HESSIAN, KRYO, PROTOSTUFF), default: HESSIAN
     */
    private String serialization;

    /**
     * Communication protocols, such as netty and http, are netty by default
     */
    private String transport;

    /**
     * Registration center, such as (zookeeper, nacos, etc.), defaults to: zookeeper
     */
    private String registry;

    /**
     * Service discovery (registry) address. The default is "127.0.0.1:2181"
     */
    private String registryAddr;

    private Integer timeout;

    public RpcClientProperties() {
        this.loadbalance = "random";
        // 把信息转化成二进制
        this.serialization = "HESSIAN";
        this.transport = "netty";
        this.registry = "zookeeper";
        this.registryAddr = "127.0.0.1:2181";
        this.timeout = 5000;
    }
}
