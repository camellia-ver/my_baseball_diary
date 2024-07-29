package com.jyr.my_baseball_diary.service;

import com.jyr.my_baseball_diary.domain.Role;
import com.jyr.my_baseball_diary.domain.User;
import com.jyr.my_baseball_diary.domain.UserRole;
import com.jyr.my_baseball_diary.dto.AddUserRequest;
import com.jyr.my_baseball_diary.repository.RoleRepository;
import com.jyr.my_baseball_diary.repository.UserRepository;
import com.jyr.my_baseball_diary.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Transactional
    public Long join(AddUserRequest dto) {
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            roleRepository.save(Role.builder().name("ROLE_USER").build());
            role = roleRepository.findByName("ROLE_USER");
        }

        Long id = userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .userName(dto.getUserName())
                .favoriteTeamId(Long.parseLong(dto.getFavoriteTeamId()))
                .createDate(LocalDateTime.now())
                .build()).getId();

        System.out.println(id);

        userRoleRepository.save(UserRole.builder()
                .userId(id)
                .roleId(role.getId())
                .build());

        return id;
    }
}
