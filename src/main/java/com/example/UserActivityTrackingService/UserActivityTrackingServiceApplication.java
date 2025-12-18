package com.example.UserActivityTrackingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UserActivityTrackingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserActivityTrackingServiceApplication.class, args);
	}

}
