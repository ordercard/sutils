package com.spring.sutils.util;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午4:38 2018/7/31 2018
 * @Modify:
 */
public enum LwReturnCodeEnum implements ReturnCode{
    /** 服务器错误 */
    COM_INTERNAL_SERVER_ERROR(500, "服务器错误"),

    ENUM_STATUS_TYPE(10001, "%S 枚举类型不存在"),

    COM_EXCEPTION_INSERT(10002, "%S 数据插入异常"),

    COM_EXCEPTION_UPDATE(10003, "%S 数据修改异常"),

    COM_EXCEPTION_SESSION(10004, "会话已过期，请重新登录"),

    COM_EXCEPTION_API_OCCUR_ERROR(400001 , "调用接口发生异常"),

    COM_EXCEPTION_API_RETURN_FAIL(400002 , "调用接口返回失败"),

    COM_LW_REQUEST_API_USERID_NOTNULL(400003, "用户名不能为空"),

    COM_LW_REQUEST_API_PWD_NOTNULL(400004, "密码不能为空"),

    COM_LW_REQUEST_API_AREA_CODE_NOTNULL(400005, "区号不能为空"),

    COM_LW_REQUEST_API_MOBILE_NOTNULL(400006, "手机号不能为空"),

    COM_LW_REQUEST_API_CAPTCHA_NOTNULL(400007, "验证码不能为空"),

    COM_LW_REQUEST_API_CAPTCHA_ERROR(400008, "验证码错误"),

    COM_LW_REQUEST_API_MOBILE_EXIST(400009, "手机号已注册"),

    COM_LW_REQUEST_API_MOBILE_NOT_EXIST(400010, "该手机号未注册，请先完成注册"),

    COM_LW_REQUEST_API_LOGIN_ERROR(400011, "用户名或密码错误"),

    COM_LW_REQUEST_API_NIKENAME_EXIST(400012, "昵称已存在"),

    COM_LW_REQUEST_API_LOGIN_FORBIDDEN(400013, "该用户已被禁用，请联系客服"),

    COM_LW_REQUEST_API_SALESMAN_NAME_NOTNULL(400014, "姓名不能为空");

    private int code;
    private String message;
    private LwReturnCodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    };
    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
