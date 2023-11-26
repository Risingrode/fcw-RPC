package com.wxy.rpc.client.annotation;

import java.lang.annotation.*;


/**
 * RPC 引用注解，自动注入对应的实现类
 * @Author: fcw
 * @Description: 该注解用于标识需要引用的远程服务，并实现自动注入相应的实现类。
 * @Date: 2023-11-25   23:11
 */

// 该注解可以用于类的字段（Field）、方法（Method）和其他注解（Annotation Type）。
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
// 1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
// 2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
// 3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RpcReference {

    /**
     * 对外暴露服务的接口类型，默认为 void.class
     */
    Class<?> interfaceClass() default void.class;

    /**
     * 对外暴露服务的接口名（全限定名），默认为 ""
     */
    String interfaceName() default "";

    /**
     * 版本号，默认 1.0
     */
    String version() default "1.0";

    /**
     * 负载均衡策略，合法的值包括：random, roundrobin, leastactive
     */
    String loadbalance() default "";

    /**
     * Service mock name, use interface name + Mock if not set
     */
    String mock() default "";

    /**
     * 服务调用超时时间
     */
    int timeout() default 0;

}
