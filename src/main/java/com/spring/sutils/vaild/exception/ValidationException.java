package com.spring.sutils.vaild.exception;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午9:43 2018/7/3 2018
 * @Modify:
 */

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
