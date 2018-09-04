package edu.njust.web.login.service;

import edu.njust.web.login.constant.Parameters;
import edu.njust.web.login.dto.UserToken;
import edu.njust.web.login.util.CharUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenManager {

    private  static Map<String, UserToken> tokenMap = new HashMap<>();
    private  static Map<Integer, UserToken> idMap = new HashMap<>();

    public static UserToken generateToken(Integer userId){

        String token = CharUtil.getRandomString(32);
        while (tokenMap.containsKey(token)) {
            token = CharUtil.getRandomString(32);
        }

        LocalDateTime update = LocalDateTime.now();
        LocalDateTime expire = update.plusMinutes(Parameters.tokenExpireTime);

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

    public static UserToken isTokenValid(String userToken){
        if(tokenMap.containsKey(userToken)){
            return tokenMap.get(userToken);
        }
        return null;
    }
}
