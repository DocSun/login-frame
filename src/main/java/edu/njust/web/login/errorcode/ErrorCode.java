package edu.njust.web.login.errorcode;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //登陆相关的错误 10 开头
    PARAMETER_NULL_ERROR(1000, "the parameter can not be null", "参数不能为空"),
    USER_NOT_EXISTED_ERROR(1001, "the user is not existed", "用户不存在"),
    USERNAME_OR_PASSWD_ERROR(1002, "the username or password is error", "用户名或者密码错误"),

    //注册相关的错误 20 开头
    REGIST_USESRNAME_EXISTED_ERROR(2001, "the username has registed", "用户名已注册"),
    REGIST_MOBILE_EXISTED_ERROR(2002, "the mobile has registed", "手机号已注册"),
    CACHECODE_ERROR(2003, "the cachecode is error", "验证码错误"),
    INSERT_USER_TO_DB_ERROR(2004, "insert user to db error", "用户插入数据库错误");

    ErrorCode(Integer code, String msg, String error){
        this.code = code;
        this.msg = msg;
        this.error = error;
    }

    private Integer code;
    private String msg;
    private String error;

}
