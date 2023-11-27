package com.wxy.rpc.server.annotation;

import java.lang.annotation.*;

/**
 *
 * @Author: fcw
 * @Description: Rpc Service 注解，标注该类为服务实现类
 * @Date: 2023-11-27   9:06
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
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
