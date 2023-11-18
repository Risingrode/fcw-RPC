package com.wxy.rpc.client.handler;

import com.wxy.rpc.core.common.RpcResponse;
import com.wxy.rpc.core.constant.ProtocolConstants;
import com.wxy.rpc.core.enums.MessageType;
import com.wxy.rpc.core.enums.SerializationType;
import com.wxy.rpc.core.protocol.MessageHeader;
import com.wxy.rpc.core.protocol.RpcMessage;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Rpc 响应消息处理器
 *
 * @author Wuxy
 * @version 1.0
 * @ClassName RpcResponseHandler
 * @Date 2023/1/6 19:07
 */
@Slf4j
// 继承的这个东西能够自动释放处理完毕的消息对象，还可以处理特定类型的消息
public class RpcResponseHandler extends SimpleChannelInboundHandler<RpcMessage> {

    /**
     * 存放未处理的响应请求
     */
    // 在发起一个 RPC 请求时，会创建一个 Promise 对象，并将其存放到 UNPROCESSED_RPC_RESPONSES 中
    // CurrentHashMap 是线程安全的 分段锁 适用于读多写少
    public static final Map<Integer, Promise<RpcMessage>> UNPROCESSED_RPC_RESPONSES = new ConcurrentHashMap<>();

    @Override
    // 读取到消息时会调用这个方法
    protected void channelRead0(ChannelHandlerContext ctx, RpcMessage msg) throws Exception {
        try {
            MessageType type = MessageType.parseByType(msg.getHeader().getMessageType());
            // 如果是 RpcRequest 请求
            if (type == MessageType.RESPONSE) {
                int sequenceId = msg.getHeader().getSequenceId();
                // 拿到还未执行完成的 promise 对象
                Promise<RpcMessage> promise = UNPROCESSED_RPC_RESPONSES.remove(sequenceId);
                if (promise != null) {
                    Exception exception = ((RpcResponse) msg.getBody()).getExceptionValue();
                    if (exception == null) {
                        // 将成功的响应消息设置到 Promise 对象中
                        promise.setSuccess(msg);
                    } else {
                        promise.setFailure(exception);
                    }
                }
            } else if (type == MessageType.HEARTBEAT_RESPONSE) { // 如果是心跳检查请求
                log.debug("Heartbeat info {}.", msg.getBody());
            }
        } finally {
            // 释放内存，防止内存泄漏
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 用户自定义事件处理器，处理写空闲，当检测到写空闲发生自动发送一个心跳检测数据包
     *
     * @param ctx ctx
     * @param evt evt
     * @throws Exception ex
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 判断事件类型是否是空闲状态事件
        // IdleStateEvent 是 Netty 提供的空闲状态检测的处理器
        if (evt instanceof IdleStateEvent) {
            // IdleState.WRITER_IDLE: 写空闲状态，表示一段时间内没有数据写入 读操作同理
            if (((IdleStateEvent) evt).state() == IdleState.WRITER_IDLE) {
                log.warn("Write idle happen [{}].", ctx.channel().remoteAddress());
                // 构造 心跳检查 RpcMessage
                RpcMessage rpcMessage = new RpcMessage();
                MessageHeader header = MessageHeader.build(SerializationType.KRYO.name());
                header.setMessageType(MessageType.HEARTBEAT_REQUEST.getType());
                rpcMessage.setHeader(header);
                // 设置一个心跳检测的数据常量
                rpcMessage.setBody(ProtocolConstants.PING);
                // 发送心跳检测请求
                ctx.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
            // 保留父类ChannelHandler对于其他用户自定义事件的默认处理行为
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * Called when an exception occurs in processing a client message
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("client catch exception：", cause);
        cause.printStackTrace();
        ctx.close();
    }
}
