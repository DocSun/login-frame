package edu.njust.web.login.manager;

import edu.njust.web.login.constant.Parameters;
import edu.njust.web.login.manager.entity.UserToken;
import edu.njust.web.login.util.CharUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TokenManager {

    private  static Map<String, UserToken> tokenMap = new HashMap<>();

    public static UserToken generateToken(){

        String token = CharUtil.getRandomString(32);
        while (tokenMap.containsKey(token)) {
            token = CharUtil.getRandomString(32);
        }

        LocalDateTime update = LocalDateTime.now();
        LocalDateTime expire = update.plusMinutes(Parameters.TOKEN_EXPIRE_TIME);

        UserToken userToken = new UserToken();
        userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUpdateTime(update);
        userToken.setExpireTime(expire);
        tokenMap.put(token, userToken);
        return userToken;
    }

    public static String getValidUserToken(String tokenString){
        UserToken userToken = tokenMap.get(tokenString);
        if(null == userToken){
            return null;
        }

        if(userToken.getExpireTime().isBefore(LocalDateTime.now())){
            tokenMap.remove(tokenString);
            return null;
        }
        return userToken.getToken();
    }

}
