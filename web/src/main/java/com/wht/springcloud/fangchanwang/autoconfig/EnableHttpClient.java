package com.wht.springcloud.fangchanwang.autoconfig;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * spring.factories 可以是配置生效，使用注解同样可以生效，即使这个类不在application得子包下面
 * 然后将该注解  注解到application上即可
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HttpClientAutoConfiguration.class)
public @interface EnableHttpClient {
}
