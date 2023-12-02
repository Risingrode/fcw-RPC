package com.wxy.rpc.core.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 *
 * @Author: fcw
 * @Description: Rpc 消息解码器
 * @Date: 2023-12-02   17:16
 */

public class RpcMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // todo: implement this method
         // Check if there are at least 4 bytes (int) in the ByteBuf
        if (in.readableBytes() < 4) {
            return;
        }

        // Mark the current position in the ByteBuf
        in.markReaderIndex();

        // Read the length of the message
        int length = in.readInt();

        // Check if the ByteBuf has enough bytes for the message
        if (in.readableBytes() < length) {
            // Not enough bytes - reset the reader index and return
            in.resetReaderIndex();
            return;
        }

        // Read the message
        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        // Convert the bytes to a string and add it to the output list
        String message = new String(bytes);
        out.add(message);
    }
}
