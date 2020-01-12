package com.lhmai.funnytube.service;

import com.lhmai.funnytube.domain.UserEntity;
import com.lhmai.funnytube.service.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserEntity findByUsername(String email);

    UserEntity save(UserRegistrationDto registration);
}
