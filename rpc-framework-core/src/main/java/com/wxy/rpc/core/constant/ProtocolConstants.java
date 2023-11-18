package com.wxy.rpc.core.constant;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 协议常量类
 *
 * @author Wuxy
 * @version 1.0
 * @ClassName ProtocolConstant
 * @Date 2023/1/5 17:32
 */
public class ProtocolConstants {
    // 序列号
    private static final AtomicInteger ai = new AtomicInteger();

    /**
     * 魔数，用来第一时间判断是否无效数据包
     * 数据定义标签，用于标识数据包的类型
     */
    public static final byte[] MAGIC_NUM = new byte[]{(byte) 'w', (byte) 'r', (byte) 'p', (byte) 'c'};

    public static final byte VERSION = 1;
    // 发送的数据包
    public static final String PING = "ping";
    // 接收的数据包
    public static final String PONG = "pong";

    // 获取序列号
    public static int getSequenceId() {
        // 实现原子操作
        return ai.getAndIncrement();
    }

}
