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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                .note(dto.getNote())
                .build());
    }

    public Boolean isDiary(LocalDate date) {
        return !diaryRepository.findByDate(date).isEmpty();
    }

    public Boolean isGameData(LocalDate date) {
        return !gameDataRepository.findByDate(date).isEmpty();
    }

    public List<LineUp> findLineUp(LocalDate date,String teamName) {
        List<LineUp> lineUpList =  lineUpRepository.findByDate(date);
        List<LineUp> lineUpsByTeamName = new ArrayList<>();

        for (LineUp lineUp : lineUpList) {
            if (lineUp.getTeamName().equals(teamName)) {
                lineUpsByTeamName.add(lineUp);
            }
        }

        List<LineUp> data1 = new ArrayList<>();
        List<LineUp> data2 = new ArrayList<>();

        Time time1 = lineUpsByTeamName.get(0).getStartGame();

        for (LineUp lineUp : lineUpsByTeamName) {
            if (lineUp.getStartGame().compareTo(time1) == 0) {
                data1.add(lineUp);
            } else {
                data2.add(lineUp);
            }
        }

        if (data2.isEmpty()) {
            return data1;
        }

        Time time2 = data2.get(0).getStartGame();

        return time1.compareTo(time2) < 0 ? data1 : data2;
    }

    public GameData findGameData(LocalDate date,String teamName) {
        List<GameData> gameDataList =  gameDataRepository.findByDate(date);
        List<GameData> gameDataByTeamName = new ArrayList<>();

        for (GameData gameData : gameDataList) {
            if (gameData.getTeamName().equals(teamName)) {
                gameDataByTeamName.add(gameData);
            }
        }

        if (gameDataByTeamName.size() > 1) {
            Time time1 = gameDataByTeamName.get(0).getStartGame();
            Time time2 = gameDataByTeamName.get(1).getStartGame();

            return time1.compareTo(time2) < 0 ? gameDataByTeamName.get(0) : gameDataByTeamName.get(1);
        }

        return gameDataByTeamName.get(0);
    }

    public List<Diary> findDiaryData(LocalDate date) {
        return diaryRepository.findByDate(date);
    }
}
