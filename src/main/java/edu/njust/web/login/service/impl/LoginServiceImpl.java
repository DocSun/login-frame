package edu.njust.web.login.service.impl;

import edu.njust.web.login.dao.model.UserInfoPO;
import edu.njust.web.login.dto.UserInfoVO;
import edu.njust.web.login.dto.UserToken;
import edu.njust.web.login.service.LoginService;
import edu.njust.web.login.service.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    public UserInfoPO findUserByName(String username){

        UserInfoPO user = new UserInfoPO();
        if(username.equals("zhangsan")){
            user.setUsername(username);
            user.setPassword("$2a$10$aioJMNOVCfwyNYnC0zgmjOg3FB562rWhzQqJ.JW56EfrFcUkwCyw6");
            user.setUserId(2);
            user.setMobile("15822223333");
            user.setSex(1);
            return user;
        }
        return null;
    }

    @Autowired

    @Override
    public UserInfoVO login(UserInfoPO userInfoPO) {
        //userInfo
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUsername(userInfoPO.getUsername());
        userInfoVO.setMobile(userInfoPO.getMobile());
        userInfoVO.setSex(userInfoPO.getSex() == 0 ? '男': '女');

        //usertoken
        UserToken userToken = TokenManager.generateToken(1);
        userInfoVO.setToken(userToken.getToken());
        userInfoVO.setTokenExpireTime(userToken.getExpireTime().toString());
        return null;
    }
}
