package com.wxy.rpc.core.extension;

import java.lang.annotation.*;

/**
 *
 * @Author: fcw
 * @Description: SPI 注解，被标注的类表示为需要加载的扩展类接口
 * @Date: 2023-12-02   19:02
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPI {

}
