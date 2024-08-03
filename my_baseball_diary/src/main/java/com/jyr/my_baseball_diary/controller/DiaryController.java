package com.jyr.my_baseball_diary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class DiaryController {
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
    public String writePage() {
        return "write";
    }

    @PostMapping("/write")
    public String write() {
        return "redirect:/articlesList";
    }

    @GetMapping("/admin/test")
    public String admin() {
        return "test";
    }
}
