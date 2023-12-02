package com.wxy.rpc.core.enums;

import lombok.Getter;

/**
 *
 * @Author: fcw
 * @Description: 消息状态类
 * @Date: 2023-12-02   18:54
 */

public enum MessageStatus {

    /**
     * 成功
     */
    SUCCESS((byte) 0),

    /**
     * 失败
     */
    FAIL((byte) 1);

    @Getter
    private final byte code;

    MessageStatus(byte code) {
        this.code = code;
    }

    public static boolean isSuccess(byte code) {
        return MessageStatus.SUCCESS.code == code;
    }

}
