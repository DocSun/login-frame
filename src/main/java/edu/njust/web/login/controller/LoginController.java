package edu.njust.web.login.controller;

import edu.njust.web.login.annotation.LoginUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping("/login")
    public String login(@LoginUser Integer userId){

        System.out.println("userId = " + userId);
        return String.valueOf(userId);
    }

}
