package com.jyr.my_baseball_diary.controller;

import com.jyr.my_baseball_diary.domain.GameData;
import com.jyr.my_baseball_diary.domain.LineUp;
import com.jyr.my_baseball_diary.domain.User;
import com.jyr.my_baseball_diary.dto.DiaryForm;
import com.jyr.my_baseball_diary.repository.DiaryRepository;
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
import java.time.LocalDateTime;
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

        boolean isTodayData = true;
        LocalDate date = LocalDate.now();

        while (!diaryService.isGameData(date)) {
            date = date.minusDays(1);
            isTodayData = false;
        }
        System.out.println(user.getFavoriteTeam());
        List<LineUp> lineUp = diaryService.findLineUp(date,user.getFavoriteTeam());
        GameData gameData = diaryService.findGameData(date, user.getFavoriteTeam());

        model.addAttribute("showPopup", isTodayData);
        model.addAttribute("redirectUrl", "main");
        model.addAttribute("lineUp", lineUp);
        model.addAttribute("gameData", gameData);

        if (diaryService.isDiary(date))
        {
            model.addAttribute("diaryContent",diaryService.findDiaryData(date));
        }

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
