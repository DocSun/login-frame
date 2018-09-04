package edu.njust.web.login.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserToken {

    private Integer userId;
    private String token;
    private LocalDateTime expireTime;
    private LocalDateTime updateTime;
    private String lastLoginIp;
}
