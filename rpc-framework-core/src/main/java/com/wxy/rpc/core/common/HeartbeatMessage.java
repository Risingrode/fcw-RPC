package com.wxy.rpc.core.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @Author: fcw
 * @Description: 心跳检查消息类
 * @Date: 2023-12-02   13:14
 */

@Data
@Builder
public class HeartbeatMessage implements Serializable {

    /**
     * 消息
     */
    private String msg;

}
