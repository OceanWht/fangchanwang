package com.wht.springcloud.fangchanwang.filter;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class FilterBeanConfig {

    /**
     * FilterRegistrationBean 是一个包装基类，可以将Filter包装称一个springBean
     * 1.构造filter
     * 2.配置拦截urlPattern
     * 3.利用FilterRegistrationBean进行包装
     */
    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFilter());
        filterFilterRegistrationBean.addUrlPatterns("*");
        return filterFilterRegistrationBean;
    }
}
