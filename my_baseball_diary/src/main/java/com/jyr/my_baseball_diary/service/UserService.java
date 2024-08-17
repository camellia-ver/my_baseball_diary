package com.jyr.my_baseball_diary.service;

import com.jyr.my_baseball_diary.domain.User;
import com.jyr.my_baseball_diary.repository.UserRepository;
import com.jyr.my_baseball_diary.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long join(UserDTO dto) {
        validateDuplicateUser(dto.getEmail());
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .userName(dto.getUserName())
                .favoriteTeam(dto.getFavoriteTeam())
                .createDate(LocalDateTime.now())
                .role("USER")
                .build()).getId();
    }

    public void validateDuplicateUser(String email) {
        List<User> findUsers = userRepository.findByEmail(email).stream().toList();
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public User findByUser(String email) {
        return userRepository.findByEmail(email).get();
    }
}
