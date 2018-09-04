package edu.njust.web.login.service.impl;

import edu.njust.web.login.dao.UserDao;
import edu.njust.web.login.dao.model.UserInfoPO;
import edu.njust.web.login.dto.UserInfoVO;
import edu.njust.web.login.dto.UserToken;
import edu.njust.web.login.errorcode.ErrorCode;
import edu.njust.web.login.service.UserService;
import edu.njust.web.login.util.TokenManager;
import edu.njust.web.login.util.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserDao userDao;

    /**
     * password的值是由 "zhangsan" 经过BCrypt加密后得到
     * @param username
     * @return
     */
    private UserInfoPO findUserByName(String username){

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


    @Override
    public UserInfoVO login(String username, String password) {

        UserInfoVO userInfoVO = new UserInfoVO();
        UserInfoPO userInfoPO = findUserByName(username);

        //用户不存在
        if(null == userInfoPO){
            userInfoVO.setCode(ErrorCode.USER_NOT_EXISTED_ERROR.getCode());
            userInfoVO.setErrmsg(ErrorCode.USER_NOT_EXISTED_ERROR.getError());
            return userInfoVO;
        }

        //用户名和密码不匹配
        if(!bCryptPasswordEncoder.matches(password, userInfoPO.getPassword())){
            userInfoVO.setCode(ErrorCode.USERNAME_OR_PASSWD_ERROR.getCode());
            userInfoVO.setErrmsg(ErrorCode.USERNAME_OR_PASSWD_ERROR.getError());
            return userInfoVO;
        }

        userInfoVO.setUsername(userInfoPO.getUsername());
        userInfoVO.setMobile(userInfoPO.getMobile());
        userInfoVO.setSex(userInfoPO.getSex() == 0 ? '男': '女');
        UserToken userToken = TokenManager.generateToken(userInfoPO.getUserId());
        userInfoVO.setToken(userToken.getToken());
        userInfoVO.setTokenExpireTime(userToken.getExpireTime().toString());
        return userInfoVO;
    }

    @Override
    public List<UserInfoPO> queryByUsername(String username) {

        return null;
    }

    @Override
    public List<UserInfoPO> queryByMobile(String mobile) {
        return null;
    }

    @Override
    public void register(String username, String password, String mobile) {

        UserInfoPO user = new UserInfoPO();

        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setMobile(mobile);
        user.setLastLoginIp(IpUtil.client(request));
//        user.setAddTime(LocalDateTime.now());
        userDao.insertUser(user);


        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());

        // token
        UserToken userToken = UserTokenManager.generateToken(user.getId());

    }
}
