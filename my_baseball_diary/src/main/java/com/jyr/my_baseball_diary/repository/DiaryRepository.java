package com.jyr.my_baseball_diary.repository;

import com.jyr.my_baseball_diary.domain.Diary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Query("SELECT d FROM Diary d WHERE d.gameDate = :date")
    List<Diary> findByGameDate(@Param("date") LocalDate date);

    @Modifying
    @Query("UPDATE Diary d SET d.title = :title, d.content = :content, d.mvp = :mvp, d.date = :date, d.startGame = :startGame, d.gameDate = :gameDate WHERE d.id = :id")
    int updateDiary(Long id, String title, String content, String mvp, LocalDateTime date, Time startGame, LocalDate gameDate);
}
