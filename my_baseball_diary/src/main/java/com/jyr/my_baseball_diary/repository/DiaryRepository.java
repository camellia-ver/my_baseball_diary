package com.jyr.my_baseball_diary.repository;

import com.jyr.my_baseball_diary.domain.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<Diary> findByUserId(Long userId, Pageable pageable);

    @Modifying
    @Query("UPDATE Diary d SET d.title = :title, d.content = :content, d.mvp = :mvp, d.date = :date, d.startGame = :startGame, d.gameDate = :gameDate WHERE d.id = :id")
    void updateDiary(Long id, String title, String content, String mvp, LocalDateTime date, Time startGame, LocalDate gameDate);

    @Query("SELECT d FROM Diary d WHERE d.title LIKE %:keyword% OR d.content LIKE %:keyword% OR d.title = :keyword OR d.content = :keyword")
    Page<Diary> searchByTitleOrContent(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT d FROM Diary d WHERE CAST(d.date AS string) LIKE %:datePart% OR CAST(d.date AS string) = :datePart")
    Page<Diary> findByDatePart(@Param("datePart") String datePart, Pageable pageable);

    @Query("SELECT COUNT(u) FROM User u WHERE DATE(u.date) = :date")
    long countByDate(@Param("date") LocalDate date);
}
