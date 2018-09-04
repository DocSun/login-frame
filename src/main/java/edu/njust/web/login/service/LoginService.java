package edu.njust.web.login.service;

import edu.njust.web.login.dao.model.UserInfoPO;
import edu.njust.web.login.dto.UserInfoVO;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    /**
     * password的值是由 "zhangsan" 经过BCrypt加密后得到
     * @param username
     * @return
     */
    public UserInfoPO findUserByName(String  username){

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

}
