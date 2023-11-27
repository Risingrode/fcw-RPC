package com.wxy.rpc.client.transport.netty;

import com.wxy.rpc.core.protocol.RpcMessage;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @Author: fcw
 * @Description: 缓存未处理完成的 rpc 请求，缓存并处理响应信息的功能已经由 {@link com.wxy.rpc.client.handler.RpcResponseHandler} 实现。
 * @Date: 2023-11-27   8:45
 */

@Deprecated
public class UnprocessedRequestCache {

    /**
     * 缓存未处理的请求响应
     */
    private static final Map<Integer, CompletableFuture<RpcMessage>> UNPROCESSED_REQUESTS = new ConcurrentHashMap<>();

    public void processResponse() {
        // do something for processing response message
    }

}
