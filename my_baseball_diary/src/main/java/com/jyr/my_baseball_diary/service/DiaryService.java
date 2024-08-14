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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        return diaryRepository.findByDate(date).isPresent();
    }

    public Boolean isGameData(LocalDate date) {
        return gameDataRepository.findByDate(date).isPresent();
    }

    public List<LineUp> findLineUp(LocalDate date) {
        return lineUpRepository.findByDate(date).stream().toList();
    }

    public List<GameData> findGameData(LocalDate date) {
        return gameDataRepository.findByDate(date).stream().toList();
    }
}
