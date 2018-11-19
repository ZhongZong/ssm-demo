package com.zzk.ssmdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author situliang
 */
@SpringBootApplication
@MapperScan("com.zzk.ssmdemo.dao")
public class SsmDemoApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SsmDemoApplication.class, args);
	}
}
