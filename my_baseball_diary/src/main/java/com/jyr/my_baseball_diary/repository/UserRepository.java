package com.jyr.my_baseball_diary.repository;

import com.jyr.my_baseball_diary.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.email = :email, u.displayName = :displayName, u.favoriteTeam = :favoriteTeam WHERE u.id = :id")
    void updateUser(@Param("id") Long id, @Param("email") String email, @Param("displayName") String displayName, @Param("favoriteTeam") String favoriteTeam);

    @Query("SELECT COUNT(u) FROM User u WHERE DATE(u.createDate) = :date")
    long countByCreateDate(@Param("date") LocalDate date);
}
