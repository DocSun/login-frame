package edu.njust.web.login.errorcode;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_NOT_EXISTED_ERROR(1001, "the user is not existed", "用户不存在"),
    USERNAME_OR_PASSWD_ERROR(1002, "the username or password is error", "用户名或者密码错误");

    ErrorCode(Integer code, String msg, String error){
        this.code = code;
        this.msg = msg;
        this.error = error;
    }

    private Integer code;
    private String msg;
    private String error;

}
