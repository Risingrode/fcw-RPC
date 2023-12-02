package com.wxy.rpc.core.config;

import lombok.Data;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * @Author: fcw
 * @Description: 线程池配置类
 * @Date: 2023-12-02   18:07
 */

@Data
public class ThreadPoolConfig {
    // 线程池默认参数
    /**
     * 核心线程数
     */
    private static final int DEFAULT_CORE_POOL_SIZE = 10;

    /**
     * 最大线程数
     */
    private static final int DEFAULT_MAX_POOL_SIZE = 100;

    /**
     * 保持活跃时间
     */
    private static final long DEFAULT_KEEP_ALIVE_TIME = 60L;

    /**
     * 时间单位，秒
     */
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    /**
     * 默认阻塞队列大小
     */
    private static final int DEFAULT_BLOCKING_QUEUE_SIZE = 10000;

    // 线程池可配置参数
    /**
     * 核心线程数
     */
    private int corePoolSize = DEFAULT_CORE_POOL_SIZE;

    /**
     * 最大线程数
     */
    private int maximumPoolSize = DEFAULT_MAX_POOL_SIZE;

    /**
     * 保持活跃时间
     */
    private long keepAliveTime = DEFAULT_KEEP_ALIVE_TIME;

    /**
     * 时间单位
     */
    private TimeUnit timeUnit = DEFAULT_TIME_UNIT;

    /**
     * 有界阻塞队列 线程安全
     */
    private BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(DEFAULT_BLOCKING_QUEUE_SIZE);
}
