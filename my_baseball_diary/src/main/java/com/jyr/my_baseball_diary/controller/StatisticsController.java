package com.jyr.my_baseball_diary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StatisticsController {
    @GetMapping("/admin/statistics")
    public String statistics(Model model) {
        return "statistics";
    }
}
