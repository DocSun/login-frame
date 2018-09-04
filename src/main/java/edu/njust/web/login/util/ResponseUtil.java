package edu.njust.web.login.util;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static String ok(){
        Map<String, Object> map= new HashMap<>();
        map.put("code", 0);
        map.put("msg", "success");
        return new Gson().toJson(map);
    }

    public static String ok(Object object) {
        return new Gson().toJson(object);
    }

    public static String failed(Integer code, String errmsg){
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", errmsg);
        return new Gson().toJson(errmsg);
    }
}
