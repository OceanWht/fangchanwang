package com.wht.springcloud.fangchanwang;

import com.wht.springcloud.fangchanwang.autoconfig.AntoConfigAnnotation;
import com.wht.springcloud.fangchanwang.autoconfig.EnableHttpClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan(value = "com.wht.springcloud.fangchanwang.mapper")
@EnableAsync
//@EnableHttpClient
//@AntoConfigAnnotation //自动依赖例子
public class FangchanwangApplication {

	public static void main(String[] args) {
		SpringApplication.run(FangchanwangApplication.class, args);
	}

}
