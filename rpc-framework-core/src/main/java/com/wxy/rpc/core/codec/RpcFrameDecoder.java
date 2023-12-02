package com.wxy.rpc.core.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 粘包拆包编码器，使用固定长度的帧解码器，通过约定用定长字节表示接下来数据的长度。<p>
 * 非共享，保存了 ByteBuf 的状态信息
 *
 * @author fcw
 * @ClassName RpcFrameDecoder
 * @Date 2023/1/5 0:09
 * @see LengthFieldBasedFrameDecoder
 */
public class RpcFrameDecoder extends LengthFieldBasedFrameDecoder {

    /**
     * 获取当前协议约定的帧解码器，默认配置为：<br>
     * 数据帧的最大长度为1024字节，<br>
     * 长度域的偏移字节数为12，<br>
     * 长度域所占的字节数为4。
     */
    public RpcFrameDecoder() {
        this(1024, 12, 4);
    }

    /**
     * 构造方法
     *
     * @param maxFrameLength    数据帧的最大长度
     * @param lengthFieldOffset 长度域的偏移字节数
     * @param lengthFieldLength 长度域所占的字节数
     */
    public RpcFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

}
