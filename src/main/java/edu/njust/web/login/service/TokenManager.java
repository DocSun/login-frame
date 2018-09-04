package edu.njust.web.login.service;

import edu.njust.web.login.constant.Parameter;
import edu.njust.web.login.dto.UserToken;
import edu.njust.web.login.util.CharUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenManager {

    public  Map<String, UserToken> tokenMap = new HashMap<>();
    public  Map<Integer, UserToken> idMap = new HashMap<>();

    public UserToken generateToken(Integer userId){

        String token = CharUtil.getRandomString(32);
        while (tokenMap.containsKey(token)) {
            token = CharUtil.getRandomString(32);
        }

        LocalDateTime update = LocalDateTime.now();
        LocalDateTime expire = update.plusMinutes(Parameter.tokenExpireTime);

        UserToken userToken = new UserToken();
        userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUpdateTime(update);
        userToken.setExpireTime(expire);
        userToken.setUserId(userId);
        tokenMap.put(token, userToken);
        idMap.put(userId, userToken);

        return userToken;
    }
}
