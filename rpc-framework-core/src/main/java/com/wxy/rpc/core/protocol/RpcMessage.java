package com.wxy.rpc.core.protocol;

import lombok.Data;

/**
 *
 * @Author: fcw
 * @Description: Rpc 消息协议类
 * @Date: 2023-12-02   17:23
 */

@Data
public class RpcMessage {

    /**
     * 请求头 - 协议信息
     */
    private MessageHeader header;

    /**
     * 消息体
     */
    private Object body;

}
