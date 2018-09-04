package edu.njust.web.login.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoVO {
    private String username;
    private char sex;
    private String mobile;
    private String token;
    private String tokenExpireTime;
    private Integer code;
    private String errmsg;
}
