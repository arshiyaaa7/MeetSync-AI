package com.hintro.meetingintelligence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MeetingIntelligenceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingIntelligenceServiceApplication.class, args);
	}

}
