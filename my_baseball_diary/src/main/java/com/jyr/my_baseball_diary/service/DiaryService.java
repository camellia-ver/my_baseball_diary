package com.jyr.my_baseball_diary.service;

import com.jyr.my_baseball_diary.domain.Diary;
import com.jyr.my_baseball_diary.dto.DiaryForm;
import com.jyr.my_baseball_diary.repository.DiaryRepository;
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

    public Boolean checkDoubleheader() {
        int num = diaryRepository.findByDate(LocalDate.now()).stream().toList().size();
        return num == 2;
    }

    @Transactional
    public void save(DiaryForm dto) {
        diaryRepository.save(Diary.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .mvp(dto.getMvp())
                .location(dto.getLocation())
                .date(LocalDateTime.now())
                .note(dto.getNote())
                .build());
    }
}
