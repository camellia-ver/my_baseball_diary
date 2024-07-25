package com.jyr.my_baseball_diary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainCotroller {
    @GetMapping("/")
    public String main() {
        return "main";
    }
}
