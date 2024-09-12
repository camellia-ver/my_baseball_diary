package com.jyr.my_baseball_diary.service;

import com.jyr.my_baseball_diary.domain.User;
import com.jyr.my_baseball_diary.repository.UserRepository;
import com.jyr.my_baseball_diary.dto.UserDTO;
import com.jyr.my_baseball_diary.utill.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CommonUtils commonUtils;

    @Transactional
    public void join(UserDTO dto) {
        validateDuplicateUser(dto.getEmail());
        userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .displayName(dto.getDisplayName())
                .favoriteTeam(dto.getFavoriteTeam())
                .createDate(LocalDateTime.now())
                .role("USER")
                .build());
    }

    @Transactional
    public void update(UserDTO dto) {
        validateDuplicateUser(dto.getEmail());
        commonUtils.getCurrentUser().ifPresent(
                user -> userRepository.updateUser(
                        user.getId(), dto.getEmail(),
                        dto.getDisplayName(), dto.getFavoriteTeam()));
    }

    private void validateDuplicateUser(String email) {
        List<User> findUsers = userRepository.findByEmail(email).stream().toList();
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이메일 주소입니다.");
        }
    }

    public User findByUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + email));
    }
}
