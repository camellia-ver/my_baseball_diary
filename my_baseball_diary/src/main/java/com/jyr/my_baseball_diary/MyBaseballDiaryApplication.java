package com.jyr.my_baseball_diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MyBaseballDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBaseballDiaryApplication.class, args);
	}

}
