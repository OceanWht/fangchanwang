package com.wht.springcloud.fangchanwang;

import com.wht.springcloud.fangchanwang.autoconfig.EnableHttpClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.wht.springcloud.fangchanwang.mapper")
@EnableHttpClient
public class FangchanwangApplication {

	public static void main(String[] args) {
		SpringApplication.run(FangchanwangApplication.class, args);
	}

}
