package edu.njust.web.login.controller;

import edu.njust.web.login.annotation.LoginUser;
import edu.njust.web.login.dto.UserInfoVO;
import edu.njust.web.login.errorcode.ErrorCode;
import edu.njust.web.login.service.UserService;
import edu.njust.web.login.util.JacksonUtil;
import edu.njust.web.login.util.RegexUtil;
import edu.njust.web.login.util.ResponseUtil;
import edu.njust.web.login.util.TokenManager;
import edu.njust.web.login.util.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    TokenManager tokenManager;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/login")
    public String login(String username, String password) {

        if (null == username || username.isEmpty() || null == password || password.isEmpty()) {
            return ResponseUtil.failed(ErrorCode.PARAMETER_NULL_ERROR.getCode(), ErrorCode.PARAMETER_NULL_ERROR.getError());
        }

        UserInfoVO userInfoVO = userService.login(username, password);
        if (userInfoVO.getCode() != 0) {
            return ResponseUtil.failed(userInfoVO.getCode(), userInfoVO.getErrmsg());
        }
        return ResponseUtil.ok(userInfoVO);
    }

    @GetMapping("/token")
    public String test(@LoginUser String token, String name) {
        System.out.println(token + "    " + name);
        return token;
    }

    @PostMapping("/register")
    public String register(@RequestBody String body,, HttpServletRequest request) {

        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");

        if (null == username || username.isEmpty() || null == password || password.isEmpty()
                || RegexUtil.isMobileExact(mobile) || null == code || code.isEmpty()) {
            return ResponseUtil.failed(ErrorCode.PARAMETER_NULL_ERROR.getCode(), ErrorCode.PARAMETER_NULL_ERROR.getError());
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


        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", userToken.getToken());
        result.put("tokenExpire", userToken.getExpireTime().toString());
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

}
