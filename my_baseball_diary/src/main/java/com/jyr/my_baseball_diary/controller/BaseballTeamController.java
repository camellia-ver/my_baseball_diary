package com.jyr.my_baseball_diary.controller;

import com.jyr.my_baseball_diary.dto.BaseballTeamDTO;
import com.jyr.my_baseball_diary.service.BaseballTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class BaseballTeamController {
    private final BaseballTeamService baseballTeamService;

    @GetMapping("/admin/teamList")
    public String teamList(Model model) {
        model.addAttribute("teamList", baseballTeamService.findBaseballTeam());

        return "teamList";
    }

    @GetMapping("/admin/teamForm")
    public String teamForm() {
        return "teamForm";
    }

    @PostMapping("/addTeam")
    public String addTeam(BaseballTeamDTO request) {
        return "teamList";
    }
}
