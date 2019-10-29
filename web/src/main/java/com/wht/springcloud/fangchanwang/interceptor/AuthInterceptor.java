package com.wht.springcloud.fangchanwang.interceptor;

import com.wht.springcloud.fangchanwang.constants.CommonConstants;
import com.wht.springcloud.fangchanwang.model.UserModel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * HandlerInterceptor spring提供的拦截器接口
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {


    /**
     * 在controller执行之前执行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String reqUri = request.getRequestURI();
        if (reqUri.startsWith("/static") || reqUri.startsWith("/error")) {
            return true;
        }
        HttpSession session = request.getSession(true);
        UserModel userModel = (UserModel) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
        if (userModel != null) {
            //放入threadlocal
            UserContext.setUser(userModel);
        }
        return true;
    }

    /**
     * 在controller执行之后执行
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 页面渲染之后执行
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //threadlocal移除
        UserContext.remove();
    }
}
