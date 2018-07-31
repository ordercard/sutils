package com.spring.sutils.util;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 7026776142859331595L;
    private static final int DEFAULT_ERROR_CODE = 500;
    private int reCode;
    private String reMsg;
    private Map<String, Object> extendParams = new HashMap();

    public BaseException(ReturnCode returnCode, Object... args) {
        this.initCodeMsg(returnCode.getCode(), returnCode.getMessage(), args);
    }

    public BaseException(Throwable cause, ReturnCode returnCode, Object... args) {
        super(cause);
        this.initCodeMsg(returnCode.getCode(), returnCode.getMessage(), args);
    }

    public BaseException(int code, String message, Object... args) {
        this.initCodeMsg(code, message, args);
    }

    public BaseException(Throwable cause, int code, String message, Object... args) {
        super(cause);
        this.initCodeMsg(code, message, args);
    }

    public BaseException(String message, Object... args) {
        this.initCodeMsg(this.getDefaultErrorCode(), message, args);
    }

    public BaseException(Throwable cause, String message, Object... args) {
        super(cause);
        this.initCodeMsg(this.getDefaultErrorCode(), message, args);
    }

    protected int getDefaultErrorCode() {
        return 500;
    }

    private void initCodeMsg(int code, String msg, Object[] args) {
        this.reCode = code;
        if (args != null && args.length != 0) {
            this.reMsg = String.format(msg, args);
        } else {
            this.reMsg = msg;
        }

    }

    public BaseException addParam(String name, Object param) {
        this.extendParams.put(name, param);
        return this;
    }

    public Object getParam(String name) {
        return this.extendParams.get(name);
    }

    public String getString(String name) {
        Object obj = this.extendParams.get(name);
        return obj == null ? null : obj.toString();
    }

    public Integer getInt(String name) {
        Object obj = this.extendParams.get(name);
        return obj == null ? null : Integer.valueOf(obj.toString());
    }

    public Map<String, Object> getParamMap() {
        return this.extendParams;
    }

    public int getReturnCode() {
        return this.reCode;
    }

    public String getReturnMessage() {
        return this.reMsg;
    }

    @Override
    public String getMessage() {
        String statusLine = String.format(" %s | %s ", this.reCode, this.reMsg);
        StringBuilder message = new StringBuilder(statusLine);
        if (!this.extendParams.isEmpty()) {
            message.append('[');
            boolean start = true;

            String key;
            for(Iterator var4 = this.extendParams.keySet().iterator(); var4.hasNext(); message.append(key).append("=").append(this.extendParams.get(key))) {
                key = (String)var4.next();
                if (start) {
                    start = false;
                } else {
                    message.append(" | ");
                }
            }

            message.append(']');
        }

        return message.toString();
    }
}
interface ReturnCode {
    int getCode();

    String getMessage();
}