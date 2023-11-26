package com.wxy.rpc.client.transport;

import com.wxy.rpc.client.common.RequestMetadata;
import com.wxy.rpc.core.protocol.RpcMessage;

/**
 *
 * @Author: fcw
 * @Description: Rpc 客户端类，负责向服务端发起请求（远程过程调用）
 * @Date: 2023-11-26   14:03
 */

public interface RpcClient {

    /**
     * 发起远程过程调用
     *
     * @param requestMetadata rpc 请求元数据
     * @return 响应结果
     */
    RpcMessage sendRpcRequest(RequestMetadata requestMetadata);

}
