package com.wht.springcloud.fangchanwang.autoconfig;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoConfigDemo.class)
@Documented
public @interface AntoConfigAnnotation {
}
