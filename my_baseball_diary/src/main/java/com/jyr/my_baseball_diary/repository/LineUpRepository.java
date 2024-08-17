package com.jyr.my_baseball_diary.repository;

import com.jyr.my_baseball_diary.domain.LineUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LineUpRepository extends JpaRepository<LineUp, Long> {
    @Query("SELECT l FROM LineUp l WHERE l.date = :date")
    List<LineUp> findByDate(@Param("date") LocalDate date);

}
