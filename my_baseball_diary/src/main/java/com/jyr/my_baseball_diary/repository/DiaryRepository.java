package com.jyr.my_baseball_diary.repository;

import com.jyr.my_baseball_diary.domain.Diary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Query("SELECT d FROM Diary d WHERE d.date = :date")
    List<Diary> findByDate(@Param("date") LocalDate date);
}
