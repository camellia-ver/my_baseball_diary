package com.jyr.my_baseball_diary.controller;

import com.jyr.my_baseball_diary.dto.DiaryForm;
import com.jyr.my_baseball_diary.repository.DiaryRepository;
import com.jyr.my_baseball_diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping("/main")
    public String mainPage() {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(grantedAuthority -> grantedAuthority.
                                getAuthority().equals("ROLE_ADMIN"))) {
            return "admin";
        } else {
            return "articlesList";
        }
    }

    @GetMapping("/writeForm")
    public String writePage(Model model) {
        model.addAttribute("doubleheader", diaryService.checkDoubleheader());
        model.addAttribute("lineUp");
        model.addAttribute("score");
        return "write";
    }

    @PostMapping("/write")
    public String write(DiaryForm request) {
        diaryService.save(request);
        return "redirect:/articlesList";
    }

    @GetMapping("/admin/test")
    public String admin() {
        return "test";
    }
}
