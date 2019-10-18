package com.wht.springcloud.fangchanwang.aopdemo;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mu {
    String name() default "";
    int age() default 18;
    int[] score();
}
