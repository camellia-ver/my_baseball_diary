package com.jyr.my_baseball_diary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseballTeamController {
    @GetMapping("/admin/teamList")
    public String teamList() {
        return "teamList";
    }
}
