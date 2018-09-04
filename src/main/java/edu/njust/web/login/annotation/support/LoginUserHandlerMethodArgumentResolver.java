package edu.njust.web.login.annotation.support;

import edu.njust.web.login.annotation.LoginUser;
import edu.njust.web.login.constant.Parameters;
import edu.njust.web.login.util.TokenManager;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(String.class)
                && methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        String token = nativeWebRequest.getHeader(Parameters.LOGIN_TOKEN_KEY);
        if(null == token || token.isEmpty()){
            return null;
        }
        return TokenManager.getValidUserToken(token);
    }
}
