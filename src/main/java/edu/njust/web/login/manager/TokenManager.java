package edu.njust.web.login.manager;

import edu.njust.web.login.constant.Parameters;
import edu.njust.web.login.manager.entity.UserToken;
import edu.njust.web.login.util.CharUtil;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TokenManager {

    private  static Map<String, UserToken> tokenMap = new HashMap<>();
    private static Map<Integer, UserToken> idMap = new HashMap<>();

    public static UserToken generateToken(Integer userId){

        String token = CharUtil.getRandomString(32);
        while (tokenMap.containsKey(token)) {
            token = CharUtil.getRandomString(32);
        }

        LocalDateTime update = LocalDateTime.now();
        LocalDateTime expire = update.plusMinutes(Parameters.TOKEN_EXPIRE_TIME);

        UserToken userToken = new UserToken();
        userToken = new UserToken();
        userToken.setUserId(userId);
        userToken.setToken(token);
        userToken.setUpdateTime(update);
        userToken.setExpireTime(expire);
        tokenMap.put(token, userToken);
        idMap.put(userId, userToken);
        return userToken;
    }

    public static Integer getValidUserToken(String tokenString, Integer userId){
        UserToken userToken = idMap.get(userId);
        if(null == userToken){
            return null;
        }

        if(!tokenString.equals(userToken.getToken())){
            return null;
        }

        if(userToken.getExpireTime().isBefore(LocalDateTime.now())){
            tokenMap.remove(tokenString);
            idMap.remove(userId);
            return null;
        }
        return userId;
    }

}
