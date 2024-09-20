package com.jyr.my_baseball_diary.service;

import com.jyr.my_baseball_diary.domain.Diary;
import com.jyr.my_baseball_diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final DiaryRepository diaryRepository;

    public Page<Diary> search(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        if (isDate(query)) {
            return diaryRepository.findByDatePart(query, pageable);
        } else {
            return diaryRepository.searchByTitleOrContent(query, pageable);
        }
    }

    private static boolean isDate(String str) {
        return str.matches("^\\d{4}-\\d{2}-\\d{2}$") || str.matches("^\\d{1,8}$");
    }

}
