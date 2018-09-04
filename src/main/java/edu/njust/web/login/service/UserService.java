package edu.njust.web.login.service;

import edu.njust.web.login.dao.model.UserInfoPO;
import edu.njust.web.login.dto.UserInfoVO;

import java.util.List;


public interface UserService {


    public UserInfoVO login(String username, String password);

    public List<UserInfoPO> queryByUsername(String username);

    public List<UserInfoPO> queryByMobile(String mobile);

    public void register(String username, String password, String mobile);

}
