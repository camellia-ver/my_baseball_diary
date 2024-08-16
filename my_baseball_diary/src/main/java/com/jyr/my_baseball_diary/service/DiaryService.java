package com.jyr.my_baseball_diary.service;

import com.jyr.my_baseball_diary.domain.Diary;
import com.jyr.my_baseball_diary.domain.GameData;
import com.jyr.my_baseball_diary.domain.LineUp;
import com.jyr.my_baseball_diary.dto.DiaryForm;
import com.jyr.my_baseball_diary.repository.DiaryRepository;
import com.jyr.my_baseball_diary.repository.GameDataRepository;
import com.jyr.my_baseball_diary.repository.LineUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final GameDataRepository gameDataRepository;
    private final LineUpRepository lineUpRepository;

    @Transactional
    public void save(DiaryForm dto) {
        diaryRepository.save(Diary.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .mvp(dto.getMvp())
                .location(dto.getLocation())
                .date(LocalDate.now())
                .startGame(dto.getStartGame())
                .gameDate(dto.getGameDate())
                .build());
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
        List<LineUp> lineUpsByTeamName = lineUpRepository.findByDate(date).stream()
                .filter(lineUp -> lineUp.getTeamName().equals(teamName))
                .sorted(Comparator.comparing(LineUp::getStartGame)).toList();

        if (lineUpsByTeamName.isEmpty()) {
            return Collections.emptyList();
        }

        Time earliestTime = lineUpsByTeamName.get(0).getStartGame();
        return lineUpsByTeamName.stream()
                .filter(lineUp -> lineUp.getStartGame().equals(earliestTime))
                .collect(Collectors.toList());
    }


    public GameData findGameData(LocalDate date, String teamName) {
        List<GameData> gameDataByTeamName = gameDataRepository.findByDate(date).stream()
                .filter(gameData -> gameData.getTeamName().equals(teamName)).toList();

        if (gameDataByTeamName.isEmpty()) {
            return null;
        }

        return gameDataByTeamName.stream()
                .min(Comparator.comparing(GameData::getStartGame))
                .orElse(null);
    }

    public Diary findDiaryData(LocalDate date, String email) {
        List<Diary> diaryListByEmail = diaryRepository.findByDate(date).stream()
                .filter(diary -> diary.getUser().getEmail().equals(email)).toList();

        if (diaryListByEmail.isEmpty()) {
            return null;
        }

        return diaryListByEmail.stream()
                .min(Comparator.comparing(Diary::getStartGame))
                .orElse(null);
    }

    public LocalDate findLatestDateWithGameData() {
        return gameDataRepository.findTopByOrderByDateDesc();
    }
}
