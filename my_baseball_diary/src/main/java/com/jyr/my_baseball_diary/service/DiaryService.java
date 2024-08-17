package com.jyr.my_baseball_diary.service;

import com.jyr.my_baseball_diary.domain.Diary;
import com.jyr.my_baseball_diary.domain.GameData;
import com.jyr.my_baseball_diary.domain.LineUp;
import com.jyr.my_baseball_diary.domain.User;
import com.jyr.my_baseball_diary.dto.DiaryDTO;
import com.jyr.my_baseball_diary.repository.DiaryRepository;
import com.jyr.my_baseball_diary.repository.GameDataRepository;
import com.jyr.my_baseball_diary.repository.LineUpRepository;
import com.jyr.my_baseball_diary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final GameDataRepository gameDataRepository;
    private final LineUpRepository lineUpRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(DiaryDTO dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(dto.getStartGame(), formatter);
        Time time = Time.valueOf(localTime);

        diaryRepository.save(Diary.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .mvp(dto.getMvp())
                .location(dto.getLocation())
                .date(LocalDate.now())
                .startGame(time)
                .gameDate(dto.getGameDate())
                .user(getCurrentUser().orElse(null))
                .build());
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            return userRepository.findByEmail(username);
        }
        return Optional.empty();
    }

    public User getCurrentUserOrThrow() {
        return getCurrentUser().orElseThrow(() -> new RuntimeException("User not found or not authenticated"));
    }

    public Boolean isGameData(LocalDate date) {
        return gameDataRepository.existsByDate(date);
    }

    public Boolean isDoubleHeader(LocalDate date, String teamName) {
        return gameDataRepository.findByDate(date).stream()
                .filter(gameData -> gameData.getTeamName().equals(teamName))
                .count() > 1;
    }


    public List<LineUp> findLineUp(LocalDate date, String teamName) {
        return lineUpRepository.findByDate(date).stream()
                .filter(lineUp -> lineUp.getTeamName().equals(teamName))
                .collect(Collectors.groupingBy(LineUp::getStartGame))
                .entrySet().stream()
                .min(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElse(Collections.emptyList());
    }

    public GameData findGameData(LocalDate date, String teamName) {
        return gameDataRepository.findByDate(date).stream()
                .filter(gameData -> gameData.getTeamName().equals(teamName))
                .min(Comparator.comparing(GameData::getStartGame))
                .orElse(null);
    }

    public Diary findGameDayDiaryData(LocalDate date, String email) {
        return diaryRepository.findByDate(date).stream()
                .filter(diary -> diary.getUser().getEmail().equals(email))
                .filter(diary -> diary.getStartGame() != null)
                .min(Comparator.comparing(Diary::getStartGame))
                .orElse(null);
    }

    public LocalDate findLatestDateWithGameData() {
        List<LocalDate> dates = gameDataRepository.findTopDates();
        return dates.isEmpty() ? null : dates.get(0);
    }


    public Diary findNoGameDayDiaryData(LocalDate date, String email) {
        return diaryRepository.findByDate(date).stream()
                .filter(diary -> diary.getUser().getEmail().equals(email))
                .filter(diary -> diary.getStartGame() == null)
                .findFirst()
                .orElse(null);
    }
}
