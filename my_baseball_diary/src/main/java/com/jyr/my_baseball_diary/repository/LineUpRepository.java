package com.jyr.my_baseball_diary.repository;

import com.jyr.my_baseball_diary.domain.Diary;
import com.jyr.my_baseball_diary.domain.LineUp;
import com.jyr.my_baseball_diary.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface LineUpRepository extends JpaRepository<LineUp, Long> {
    Optional<LineUp> findByDate(LocalDate date);
}
