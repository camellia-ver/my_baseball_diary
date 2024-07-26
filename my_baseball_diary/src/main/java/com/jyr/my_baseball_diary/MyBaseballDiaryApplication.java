package com.jyr.my_baseball_diary;

import com.jyr.my_baseball_diary.domain.User;
import com.jyr.my_baseball_diary.dto.AddUserRequset;
import com.jyr.my_baseball_diary.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyBaseballDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBaseballDiaryApplication.class, args);
	}

}
