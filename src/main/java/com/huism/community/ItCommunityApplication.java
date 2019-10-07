package com.huism.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.huism.community.mapper")
public class ItCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItCommunityApplication.class, args);
	}

}
