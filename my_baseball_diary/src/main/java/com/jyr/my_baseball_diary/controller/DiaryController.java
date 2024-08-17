package com.jyr.my_baseball_diary.controller;

import com.jyr.my_baseball_diary.domain.Diary;
import com.jyr.my_baseball_diary.domain.GameData;
import com.jyr.my_baseball_diary.domain.LineUp;
import com.jyr.my_baseball_diary.domain.User;
import com.jyr.my_baseball_diary.dto.DiaryDTO;
import com.jyr.my_baseball_diary.service.DiaryService;
import com.jyr.my_baseball_diary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class DiaryController {
    private final DiaryService diaryService;
    private final UserService userService;

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
    public String writePage(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByUser(email);
        String favoriteTeam = user.getFavoriteTeam();

        LocalDate latestDateWithGameData = diaryService.findLatestDateWithGameData();
        LocalDate date = LocalDate.now();

        if (date.isAfter(latestDateWithGameData)) {
            date = latestDateWithGameData;
        }

        boolean isTodayData = date.equals(LocalDate.now());
        Diary diary;

        if (isTodayData) {
            List<LineUp> lineUp = diaryService.findLineUp(date, favoriteTeam);
            GameData gameData = diaryService.findGameData(date, favoriteTeam);
            boolean isDoubleHeader = diaryService.isDoubleHeader(date, favoriteTeam);
            diary = diaryService.findGameDayDiaryData(date, email);

            model.addAttribute("doubleHeader", isDoubleHeader);
            model.addAttribute("lineUp", lineUp);
            model.addAttribute("gameData", gameData);
        } else {
            diary = diaryService.findNoGameDayDiaryData(date,email);
        }

        model.addAttribute("todayGame", isTodayData);
        model.addAttribute("diaryContent",diary);

        return "write";
    }

    @PostMapping("/write")
    public String write(DiaryDTO request) {
        diaryService.save(request);
        return "redirect:/main";
    }

    @GetMapping("/admin/test")
    public String admin() {
        return "test";
    }
}
