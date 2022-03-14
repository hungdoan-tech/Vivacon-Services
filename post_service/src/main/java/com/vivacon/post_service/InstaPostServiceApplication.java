package com.vivacon.post_service;

import com.vivacon.post_service.messaging.PostEventStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableEurekaClient
@EnableMongoAuditing
@EnableBinding(PostEventStream.class)
public class InstaPostServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstaPostServiceApplication.class, args);
	}

}
