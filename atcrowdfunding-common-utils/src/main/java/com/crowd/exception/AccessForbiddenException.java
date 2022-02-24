package com.crowd.exception;

import java.io.Serializable;

/**
 *  表示用户没有登录就访问受保护资源抛出的异常
 * @author 李晓扬
 */
public class AccessForbiddenException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -2319733816113052417L;

    public AccessForbiddenException() {
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
