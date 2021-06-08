package com.wly.secondkill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.wly.secondkill.db.mappers")
@ComponentScan(basePackages = {"com.wly"})
public class SecondkillApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondkillApplication.class, args);
	}

}
