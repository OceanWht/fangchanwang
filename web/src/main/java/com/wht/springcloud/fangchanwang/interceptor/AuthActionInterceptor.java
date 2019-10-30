package com.wht.springcloud.fangchanwang.interceptor;

import com.wht.springcloud.fangchanwang.model.UserModel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Component
public class AuthActionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果用户没登录则重定向到登录页面
        UserModel userModel = UserContext.getUser();
        //如果没有登录返回错误信息，提示请先登录
        if (userModel == null) {
            String msg = URLEncoder.encode("请先登录", "UTF-8");
            String target = URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                response.sendRedirect("/accounts/signin?errorMsg=" + msg + "&target=" + target);
                return false;
            }else {
                response.sendRedirect("/accounts/signin?errorMsg=" + msg);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
