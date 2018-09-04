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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Repeatable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        if(null == username || username.isEmpty() ||  null == password || password.isEmpty()){
            return ResponseUtil.failed(ErrorCode.PARAMETER_NULL_ERROR.getCode(), ErrorCode.PARAMETER_NULL_ERROR.getError());
        }
        UserInfoPO userInfoPO = loginService.findUserByName(username);
        if(null == userInfoPO){
            return ResponseUtil.failed(ErrorCode.USER_NOT_EXISTED_ERROR.getCode(), ErrorCode.USER_NOT_EXISTED_ERROR.getError());
        }
        if(!bCryptPasswordEncoder.matches(password, userInfoPO.getPassword())){
            return ResponseUtil.failed(ErrorCode.USERNAME_OR_PASSWD_ERROR.getCode(), ErrorCode.USERNAME_OR_PASSWD_ERROR.getError());
        }

        return ResponseUtil.ok(userInfoVO);
    }

    @GetMapping("/token")
    public String test(@LoginUser String token, String name){
        System.out.println(token + "    " + name);
        return token;
    }

    @PostMapping("/register")
    public String register(UserInfoPO userInfoPO, HttpServletRequest request){

        if (userInfoPO.getUsername() == null || userInfoPO.getPassword() == null || userInfoPO.getMobile() == null || code == null) {
            return ResponseUtil.badArgument();
        }

        List<LitemallUser> userList = userService.queryByUsername(username);
        if (userList.size() > 0) {
            return ResponseUtil.fail(403, "用户名已注册");
        }

        userList = userService.queryByMobile(mobile);
        if (userList.size() > 0) {
            return ResponseUtil.fail(403, "手机号已注册");
        }
        if (!RegexUtil.isMobileExact(mobile)) {
            return ResponseUtil.fail(403, "手机号格式不正确");
        }
        //判断验证码是否正确
        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
            return ResponseUtil.fail(403, "验证码错误");

        LitemallUser user = new LitemallUser();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);

        user = new LitemallUser();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setMobile(mobile);
        user.setWeixinOpenid("");
        user.setAvatar("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg?imageView&quality=90&thumbnail=64x64");
        user.setNickname(username);
        user.setGender((byte) 0);
        user.setUserLevel((byte) 0);
        user.setStatus((byte) 0);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.client(request));
        user.setAddTime(LocalDateTime.now());
        userService.add(user);


        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());

        // token
        UserToken userToken = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", userToken.getToken());
        result.put("tokenExpire", userToken.getExpireTime().toString());
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

}
