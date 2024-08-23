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
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class DiaryController {
    private final DiaryService diaryService;
    private final UserService userService;

    @GetMapping("/main")
    public String mainPage(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(grantedAuthority -> grantedAuthority.
                                getAuthority().equals("ROLE_ADMIN"))) {
            return "admin";
        } else {
            model.addAttribute("diaryList", diaryService.findById());

            return "articlesList";
        }
    }

    @GetMapping("/writeForm")
    public String writePage(Model model, @AuthenticationPrincipal UserDetails userDetails, @RequestParam(name = "inputDate", required = false)String inputDate) {
        String email = userDetails.getUsername();
        User user = userService.findByUser(email);
        String favoriteTeam = user.getFavoriteTeam();
        Long userId = user.getId();

        LocalDate date = (inputDate == null) ? LocalDate.now() : LocalDate.parse(inputDate);

        boolean isTodayDataAvailable = diaryService.isGame(date, favoriteTeam);
        Diary diary = diaryService.findDiaryDataNotDoubleHeader(date, userId);

        if (isTodayDataAvailable) {
            Map<String, List<LineUp>> lineUp = diaryService.findLineUp(date, favoriteTeam);
            Map<String,GameData> gameData = diaryService.findGameData(date, favoriteTeam);
            boolean isDoubleHeader = diaryService.isDoubleHeader(date, favoriteTeam);

            model.addAttribute("doubleHeader", isDoubleHeader);
            model.addAttribute("pList", lineUp.get("pitcher"));
            model.addAttribute("hList", lineUp.get("hitter"));
            model.addAttribute("gameDataTeam1", gameData.get("team1"));
            model.addAttribute("gameDataTeam2", gameData.get("team2"));
        }

        model.addAttribute("todayGame", isTodayDataAvailable);
        model.addAttribute("diaryContent",diary);
        model.addAttribute("date", date);

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
