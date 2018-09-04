package edu.njust.web.login.dao.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户在数据库中存储，对应一个表
 */

@Setter
@Getter
public class UserInfoPO {

    private Integer userId;
    private String username;
    private String password;
    private String mobile;
    private Integer sex;
    private String city;
    private String lastLoginIp;

}
