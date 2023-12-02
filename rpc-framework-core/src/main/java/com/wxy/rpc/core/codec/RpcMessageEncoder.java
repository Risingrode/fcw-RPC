package com.wxy.rpc.core.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 *
 * @Author: fcw
 * @Description:  Rpc 消息编码器
 * @Date: 2023-12-02   17:16
 */

public class RpcMessageEncoder<T> extends MessageToByteEncoder<T> {

    @Override
    protected void encode(ChannelHandlerContext ctx, T msg, ByteBuf out) throws Exception {
        // todo: implement this method.
        // Convert the message to bytes
        byte[] bytes = msg.toString().getBytes();

        // Write the length of the message
        out.writeInt(bytes.length);

        // Write the message
        out.writeBytes(bytes);
    }
}
