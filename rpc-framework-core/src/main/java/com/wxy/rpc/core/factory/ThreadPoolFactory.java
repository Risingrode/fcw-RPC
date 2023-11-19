package com.wxy.rpc.core.factory;

import com.wxy.rpc.core.config.ThreadPoolConfig;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池工厂类
 *
 * @author Wuxy
 * @version 1.0
 * @ClassName ThreadPoolFactory
 * @Date 2023/1/12 12:16
 */
public class ThreadPoolFactory {

    private static final int AVAILABLE_PROCESSOR_NUMBER = Runtime.getRuntime().availableProcessors();

    private static ThreadPoolConfig threadPoolConfig;

    public ThreadPoolFactory() {
        threadPoolConfig = new ThreadPoolConfig();
    }
    // 你可以在整个应用程序中共享同一个线程池，避免了创建多个线程池可能导致的资源浪费。
    public static ThreadPoolExecutor getDefaultThreadPool() {
        return new ThreadPoolExecutor(threadPoolConfig.getCorePoolSize(),
                threadPoolConfig.getMaximumPoolSize(),
                threadPoolConfig.getKeepAliveTime(),
                threadPoolConfig.getTimeUnit(),
                threadPoolConfig.getWorkQueue());
    }

}
