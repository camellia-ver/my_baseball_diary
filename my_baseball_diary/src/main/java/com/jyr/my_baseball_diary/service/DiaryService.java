package com.jyr.my_baseball_diary.service;

import com.jyr.my_baseball_diary.domain.Diary;
import com.jyr.my_baseball_diary.dto.DiaryForm;
import com.jyr.my_baseball_diary.repository.DiaryRepository;
import com.jyr.my_baseball_diary.repository.LineUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final LineUpRepository lineUpRepository;

    public Integer NumberOfGame() {
        return lineUpRepository.findByDate(LocalDate.now()).stream().toList().size();
    }

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
}
