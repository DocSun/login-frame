package edu.njust.web.login.service;

import edu.njust.web.login.dao.model.UserInfoPO;
import edu.njust.web.login.dto.UserInfoVO;


public interface LoginService {

    /**
     * password的值是由 "zhangsan" 经过BCrypt加密后得到
     * @param username
     * @return
     */
    public UserInfoPO findUserByName(String  username);

    public UserInfoVO login(UserInfoPO userInfoPO);



}
