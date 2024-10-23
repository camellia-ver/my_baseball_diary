package com.jyr.my_baseball_diary.controller;

import com.jyr.my_baseball_diary.dto.BaseballTeamDTO;
import com.jyr.my_baseball_diary.service.BaseballTeamService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

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

    @PostMapping("/admin/addTeam")
    public String addTeam(BaseballTeamDTO request, RedirectAttributes redirectAttributes) {
        baseballTeamService.save(request);
        redirectAttributes.addFlashAttribute("message", "팀이 성공적으로 추가되었습니다.");
        return "redirect:/admin/teamList";
    }
}
