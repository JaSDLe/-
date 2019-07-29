package com.ziwu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ziwu.mapper")
public class Project41Application {

	public static void main(String[] args) {
		SpringApplication.run(Project41Application.class, args);
	}

}
