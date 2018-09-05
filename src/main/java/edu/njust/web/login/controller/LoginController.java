package edu.njust.web.login.controller;

import edu.njust.web.login.annotation.LoginUser;
import edu.njust.web.login.dao.model.UserInfoPO;
import edu.njust.web.login.dto.UserInfoVO;
import edu.njust.web.login.errorcode.ErrorCode;
import edu.njust.web.login.manager.CaptchaCodeManager;
import edu.njust.web.login.service.UserService;
import edu.njust.web.login.util.JacksonUtil;
import edu.njust.web.login.util.RegexUtil;
import edu.njust.web.login.util.ResponseUtil;
import edu.njust.web.login.manager.TokenManager;
import edu.njust.web.login.util.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

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
    public String register(@RequestBody String body, HttpServletRequest request) {

        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");
        Integer sex = JacksonUtil.parseInteger(body, "sex");
        String city = JacksonUtil.parseString(body, "city");

        UserInfoPO userInfoPO = new UserInfoPO();
        userInfoPO.setPassword(bCryptPasswordEncoder.encode(password));
        userInfoPO.setUsername(username);
        userInfoPO.setCity(city);
        userInfoPO.setMobile(mobile);
        userInfoPO.setSex(sex);

        if (null == username || username.isEmpty() || null == password || password.isEmpty()
                || RegexUtil.isMobileExact(mobile) || null == code || code.isEmpty()) {
            return ResponseUtil.failed(ErrorCode.PARAMETER_NULL_ERROR.getCode(), ErrorCode.PARAMETER_NULL_ERROR.getError());
        }


        List<UserInfoPO> userList = userService.queryByUsername(username);
        if (userList.size() > 0) {
            return ResponseUtil.failed(ErrorCode.REGIST_USESRNAME_EXISTED_ERROR.getCode(), ErrorCode.REGIST_USESRNAME_EXISTED_ERROR.getError());
        }

        userList = userService.queryByMobile(mobile);
        if (userList.size() > 0) {
            return ResponseUtil.failed(ErrorCode.REGIST_MOBILE_EXISTED_ERROR.getCode(), ErrorCode.REGIST_MOBILE_EXISTED_ERROR.getError());
        }

        //判断验证码是否正确
        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
            return ResponseUtil.failed(ErrorCode.CACHECODE_ERROR.getCode(), ErrorCode.CACHECODE_ERROR.getError());
        UserInfoVO userInfoVO = userService.register(userInfoPO);
        return userInfoVO.getCode() == 0 ? ResponseUtil.ok(userInfoVO) : ResponseUtil.failed(userInfoVO.getCode(), userInfoVO.getErrmsg());
    }

}
