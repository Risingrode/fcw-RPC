package com.wxy.rpc.core.extension;

/**
 *
 * @Author: fcw
 * @Description: Extension 工厂类
 * @Date: 2023-12-02   19:15
 */

@SPI
public interface ExtensionFactory {

    /**
     * 得到扩展对象实例
     *
     * @param type 对象类型
     * @param name 对象名称
     * @param <T>  实例类型
     * @return 返回对象实例
     */
    <T> T getExtension(Class<?> type, String name);

}
