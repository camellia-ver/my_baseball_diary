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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class DiaryController {
    private final DiaryService diaryService;
    private final UserService userService;

    @GetMapping("/main")
    public String mainPage(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(grantedAuthority -> grantedAuthority.
                        getAuthority().equals("ROLE_ADMIN"))) {
            return "admin";
        } else {
            model.addAttribute("diaryList", diaryService.findByUserId(page,size));
            return "articlesList";
        }
    }

    @GetMapping("/writeForm")
    public String writePage(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "inputDate", required = false) String inputDate,
            @RequestParam(name = "selectedTime", required = false) String selectedTime
    ) {
        String email = userDetails.getUsername();
        User user = userService.findByUser(email);
        String favoriteTeam = user.getFavoriteTeam();
        Long userId = user.getId();

        LocalDate date = (inputDate == null) ? LocalDate.now() : LocalDate.parse(inputDate);

        boolean isTodayDataAvailable = diaryService.isGame(date, favoriteTeam);
        Diary diary = diaryService.findDiaryData(date, userId);

        if (isTodayDataAvailable) {
            populateGameData(model, date, favoriteTeam, diary, selectedTime);
        }

        model.addAttribute("todayGame", isTodayDataAvailable);
        model.addAttribute("diaryContent", diary);
        model.addAttribute("date", date);

        return "write";
    }

    @GetMapping("/diaries/{id}")
    public String getDiary(
            Model model,
            @PathVariable("id") Long id,
            @RequestParam(name = "selectedTime", required = false) String selectedTime
    ) {
        Diary diary = diaryService.findById(id);
        Time startGame = diary.getStartGame();
        LocalDate gameDate = diary.getGameDate();

        if (startGame != null) {
            String favoriteTeam = diary.getUser().getFavoriteTeam();
            populateGameData(model, gameDate, favoriteTeam, diary, selectedTime);
        }

        model.addAttribute("todayGame", startGame != null);
        model.addAttribute("diaryContent", diary);
        model.addAttribute("date", gameDate);

        return "diaryView";
    }

    @PostMapping("/write")
    public String write(DiaryDTO request) {
        Long id = diaryService.save(request);
        return "redirect:/diaries/" + id;
    }

    @PostMapping("/update")
    public String update(DiaryDTO request) {
        diaryService.update(request);
        return "redirect:/main";
    }

    private void populateGameData(Model model, LocalDate date, String favoriteTeam, Diary diary, String selectedTime) {
        boolean isDoubleHeader = diaryService.isDoubleHeader(date, favoriteTeam);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Time time = diary != null ? diary.getStartGame() : null;

        if (selectedTime != null) {
            time = diaryService.findStartGame(date, favoriteTeam, selectedTime);
        }

        Map<String, List<LineUp>> lineUp = isDoubleHeader
                ? diaryService.findLineUpByGameStart(date, favoriteTeam, time)
                : diaryService.findLineUp(date, favoriteTeam);

        Map<String, GameData> gameData = isDoubleHeader
                ? diaryService.findGameDataByStartGame(date, favoriteTeam, time)
                : diaryService.findGameData(date, favoriteTeam);

        model.addAttribute("doubleHeader", isDoubleHeader);
        model.addAttribute("pList", lineUp.get("pitcher"));
        model.addAttribute("hList", lineUp.get("hitter"));
        model.addAttribute("gameDataTeam1", gameData.get("team1"));
        model.addAttribute("gameDataTeam2", gameData.get("team2"));
        model.addAttribute("startGame", sdf.format(gameData.get("team1").getStartGame()));
    }

}
