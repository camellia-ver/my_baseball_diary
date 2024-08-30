package com.jyr.my_baseball_diary.api;

import com.jyr.my_baseball_diary.domain.Diary;
import com.jyr.my_baseball_diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DiaryApiController {
    private final DiaryService diaryService;

    @GetMapping("/api/diaries/{id}")
    public ResponseEntity<Diary> findDiary(@PathVariable Long id) {
        Diary diary = diaryService.findById(id);
        return ResponseEntity.ok().body(diary);
    }

    @DeleteMapping("/api/diaries/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable("id") Long id) {
        diaryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
