package com.jyr.my_baseball_diary.service;

import com.jyr.my_baseball_diary.domain.User;
import com.jyr.my_baseball_diary.dto.AddUserRequest;
import com.jyr.my_baseball_diary.repository.UserRepository;
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
    public Long join(AddUserRequest dto) {
        validateDuplicateUser(dto);

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .userName(dto.getUserName())
                .favoriteTeamId(Long.parseLong(dto.getFavoriteTeamId()))
                .createDate(LocalDateTime.now())
                .role("USER")
                .build()).getId();
    }

    private void validateDuplicateUser(AddUserRequest dto) {
        List<User> findUsers = userRepository.findByEmail(dto.getEmail()).stream().toList();
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
