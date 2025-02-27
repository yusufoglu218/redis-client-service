package com.kafein.client.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.kafein.common.config", "com.kafein.common.exception", "com.kafein.client.redis"})
public class RedisClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisClientServiceApplication.class, args);
	}

}
