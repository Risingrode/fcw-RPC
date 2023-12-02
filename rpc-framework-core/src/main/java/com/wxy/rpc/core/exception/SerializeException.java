package com.wxy.rpc.core.exception;

/**
 *
 * @Author: fcw
 * @Description:
 * @Date: 2023-12-02   19:01
 */

public class SerializeException extends RuntimeException {

    private static final long serialVersionUID = 3365624081242234232L;

    public SerializeException() {
        super();
    }

    public SerializeException(String msg) {
        super(msg);
    }

    public SerializeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SerializeException(Throwable cause) {
        super(cause);
    }

}
