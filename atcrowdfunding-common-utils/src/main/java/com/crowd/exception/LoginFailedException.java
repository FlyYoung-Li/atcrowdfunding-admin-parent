package com.crowd.exception;

import java.io.Serializable;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/9/6 20:45
 */
public class LoginFailedException extends RuntimeException implements Serializable{

    private static final long serialVersionUID = -6603614330269105197L;

    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    public LoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
