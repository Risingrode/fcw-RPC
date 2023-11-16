package com.wxy.rpc.client.common;

import com.wxy.rpc.core.protocol.RpcMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求元数据类
 *
 * @Description 该类用于封装远程服务调用的相关信息
 * @author Wuxy
 * @version 1.0
 * @ClassName RequestMetadata
 * @Date 2023/1/7 14:06
 */
@Data
// 生成 Builder 模式相关的代码
@Builder
// 生成无参构造方法和全参构造方法。
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
