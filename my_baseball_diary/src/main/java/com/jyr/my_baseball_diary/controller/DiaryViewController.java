package com.jyr.my_baseball_diary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class DiaryViewController {
    @GetMapping("/main")
    public String mainPage() {
        return "articlesList";
    }

    @GetMapping("/admin/test")
    public String admin() {
        return "test";
    }
}
