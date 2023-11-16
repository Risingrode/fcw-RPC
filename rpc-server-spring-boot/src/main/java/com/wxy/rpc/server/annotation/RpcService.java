package com.wxy.rpc.server.annotation;

import java.lang.annotation.*;

/**
 * Rpc Service 注解，标注该类为服务实现类
 *
 * @author Wuxy
 * @version 1.0
 * @ClassName RpcService
 * @Date 2023/1/6 17:15
 */
// 表示这个注解只能标注在类上。
@Target(ElementType.TYPE)
// 指定了这个注解在运行时保留。这意味着这个注解可以通过反射机制在运行时获取到，这通常是自定义注解用于实现一些运行时处理逻辑的必要条件。
@Retention(RetentionPolicy.RUNTIME)
// 在生成文档时，使用了这个注解的类的文档中会包含这个注解的信息。
@Documented
// 指定了这个注解可以被继承。通常情况下，注解不会被子类继承。但是使用 `@Inherited` 注解后，如果一个类使用了这个注解，它的子类也会继承这个注解。
@Inherited
// @interface 说明这是一个声明式注解
public @interface RpcService {

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

}
