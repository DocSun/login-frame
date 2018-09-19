package edu.njust.web.login.controller;

import edu.njust.web.login.dto.UserInfoVO;
import edu.njust.web.login.errorcode.ErrorCode;
import edu.njust.web.login.service.UserService;
import edu.njust.web.login.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SessionLoginController {

    @Autowired
    UserService userService;

    @RequestMapping("/login/session")
    public String login(String username, String password, HttpServletRequest request){
        if (null == username || username.isEmpty() || null == password || password.isEmpty()) {
            return ResponseUtil.failed(ErrorCode.PARAMETER_NULL_ERROR.getCode(), ErrorCode.PARAMETER_NULL_ERROR.getError());
        }

        UserInfoVO userInfoVO = userService.login(username, password);
        if (userInfoVO.getCode() != 0) {
            HttpSession session = request.getSession();
            session.setAttribute("token", userInfoVO.getToken());
            return ResponseUtil.failed(userInfoVO.getCode(), userInfoVO.getErrmsg());
        }
        return ResponseUtil.ok(userInfoVO);
    }

}
