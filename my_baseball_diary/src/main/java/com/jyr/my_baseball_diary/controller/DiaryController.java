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
import java.util.Map;

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
        Long userId = user.getId();

        LocalDate latestDateWithGameData = diaryService.findLatestDateWithGameData();
        LocalDate date = LocalDate.now();

        boolean isTodayData = latestDateWithGameData.equals(date);
        Diary diary;

        if (isTodayData) {
            Map<String, List<LineUp>> lineUp = diaryService.findLineUp(date, favoriteTeam);
            Map<String,GameData> gameData = diaryService.findGameData(date, favoriteTeam);
            boolean isDoubleHeader = diaryService.isDoubleHeader(date, favoriteTeam);
            diary = diaryService.findGameDayDiaryData(date, userId);

            model.addAttribute("doubleHeader", isDoubleHeader);
            model.addAttribute("pList", lineUp.get("pitcher"));
            model.addAttribute("hList", lineUp.get("hitter"));
            model.addAttribute("gameDataTeam1", gameData.get("team1"));
            model.addAttribute("gameDataTeam2", gameData.get("team2"));
        } else {
            diary = diaryService.findNoGameDayDiaryData(date,userId);
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

    @PostMapping("/update")
    public String update(DiaryDTO request) {
        diaryService.update(request);
        return "redirect:/main";
    }

    @GetMapping("/admin/test")
    public String admin() {
        return "test";
    }
}
