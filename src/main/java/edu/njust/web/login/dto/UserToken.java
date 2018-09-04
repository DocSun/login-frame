package edu.njust.web.login.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserToken {

    private Integer userId;
    private String token;
    private LocalDateTime expireTime;
    private LocalDateTime updateTime;
}
