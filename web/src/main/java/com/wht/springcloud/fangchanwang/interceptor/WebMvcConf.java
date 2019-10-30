package com.wht.springcloud.fangchanwang.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 做拦截器的顺序管控，以及拦截器所映射的urlMapping  实现addInterceptors
 */
@Configuration
public class WebMvcConf implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Autowired
    private AuthActionInterceptor authActionInterceptor;

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //按顺序添加即可实现顺序管控
        registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");

        String[] pathPatterns = new String[]{"/accounts/profile","/house/toAdd"};

        //添加需要登录的请求
        registry.addInterceptor(authActionInterceptor).addPathPatterns(pathPatterns);
    }
}
