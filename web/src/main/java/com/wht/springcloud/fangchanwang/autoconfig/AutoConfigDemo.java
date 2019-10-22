package com.wht.springcloud.fangchanwang.autoconfig;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AutoConfigDemoClass.class)
public class AutoConfigDemo {

    @Bean
    public AutoConfigDemoClass autoConfigDemoClass(){
        return new AutoConfigDemoClass();
    }

}
