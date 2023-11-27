package com.wxy.rpc.client.common;

import com.wxy.rpc.core.protocol.RpcMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @Author: fcw
 * @Description: 该类用于封装远程服务调用的相关信息,请求元数据类
 * @Date: 2023-11-25   23:17
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestMetadata {

    /**
     * 消息协议 - （请求头协议信息 + 请求信息）
     */
    private RpcMessage rpcMessage;
    /**
     * 远程服务提供方地址
     */
    private String serverAddr;
    /**
     * 远程服务提供方端口号
     */
    private Integer port;
    /**
     * 调用超时时间
     */
    private Integer timeout;

}
