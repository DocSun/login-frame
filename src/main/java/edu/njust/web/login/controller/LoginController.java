package edu.njust.web.login.controller;

import edu.njust.web.login.annotation.LoginUser;
import edu.njust.web.login.dao.model.UserInfoPO;
import edu.njust.web.login.dto.UserInfoVO;
import edu.njust.web.login.dto.UserToken;
import edu.njust.web.login.errorcode.ErrorCode;
import edu.njust.web.login.service.LoginService;
import edu.njust.web.login.util.ResponseUtil;
import edu.njust.web.login.service.TokenManager;
import edu.njust.web.login.util.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    TokenManager tokenManager;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/login")
    public String login(String username, String password){
        UserInfoPO userInfoPO = loginService.findUserByName(username);

        if(null == userInfoPO){
            return ResponseUtil.failed(ErrorCode.USER_NOT_EXISTED_ERROR.getCode(), ErrorCode.USER_NOT_EXISTED_ERROR.getError());
        }
        if(!bCryptPasswordEncoder.matches(password, userInfoPO.getPassword())){
            return ResponseUtil.failed(ErrorCode.USERNAME_OR_PASSWD_ERROR.getCode(), ErrorCode.USERNAME_OR_PASSWD_ERROR.getError());
        }

        //userInfo
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUsername(username);
        userInfoVO.setMobile(userInfoPO.getMobile());
        userInfoVO.setSex(userInfoPO.getSex() == 0 ? '男': '女');

        //usertoken
        UserToken userToken = tokenManager.generateToken(1);
        userInfoVO.setToken(userToken.getToken());
        userInfoVO.setTokenExpireTime(userToken.getExpireTime().toString());

        return ResponseUtil.ok(userInfoVO);
    }

    @GetMapping("/token")
    public String test(@LoginUser String token, String name){
        System.out.println(token + "    " + name);
        return token;
    }

}
