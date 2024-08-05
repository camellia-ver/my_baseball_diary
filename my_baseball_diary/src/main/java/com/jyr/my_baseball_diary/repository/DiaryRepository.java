package com.jyr.my_baseball_diary.repository;

import com.jyr.my_baseball_diary.domain.Diary;
import com.jyr.my_baseball_diary.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<Diary> findByDate(LocalDate date);
}
