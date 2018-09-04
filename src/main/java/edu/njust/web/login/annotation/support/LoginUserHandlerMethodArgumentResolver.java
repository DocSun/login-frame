package edu.njust.web.login.annotation.support;

import edu.njust.web.login.annotation.LoginUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Parameter;
import java.util.Iterator;

public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(String.class)
                && methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        Parameter parameter = methodParameter.getParameter();
        String name = parameter.getName();
        System.out.println("name = " + name);
        System.out.println(methodParameter.getParameter().toString());;
        System.out.println("enter the login annotation!");
        Iterator<String> nameIterator = nativeWebRequest.getHeaderNames();
        while(nameIterator.hasNext()){
            System.out.println(nameIterator.next());
        }
        return null;
    }
}
