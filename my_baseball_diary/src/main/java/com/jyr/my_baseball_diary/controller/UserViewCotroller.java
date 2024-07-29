package com.jyr.my_baseball_diary.controller;

import com.jyr.my_baseball_diary.domain.BaseballTeam;
import com.jyr.my_baseball_diary.repository.BaseBallDataRepository;
import com.jyr.my_baseball_diary.service.BaseballDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserViewCotroller {
    private final BaseballDataService baseballDataService;

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("TeamData",baseballDataService.findTeamAll());
        return "signup";
    }
}
