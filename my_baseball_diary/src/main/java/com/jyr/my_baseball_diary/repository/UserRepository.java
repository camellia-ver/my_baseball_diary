package com.jyr.my_baseball_diary.repository;

import com.jyr.my_baseball_diary.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository{
    private final EntityManager em;

    public User findByEmail(String email) {
        return em.find(User.class, email);
    }
}
