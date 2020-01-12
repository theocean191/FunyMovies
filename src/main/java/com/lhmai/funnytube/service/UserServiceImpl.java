package com.lhmai.funnytube.service;

import com.lhmai.funnytube.domain.RoleEntity;
import com.lhmai.funnytube.domain.UserEntity;
import com.lhmai.funnytube.exception.PasswordConfirmationNotMatchException;
import com.lhmai.funnytube.exception.UserEntityNotFoundException;
import com.lhmai.funnytube.exception.UsernameExistException;
import com.lhmai.funnytube.repository.RoleRepository;
import com.lhmai.funnytube.repository.UserRepository;
import com.lhmai.funnytube.service.dto.UserRegistrationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserEntityNotFoundException::new);
    }

    public UserEntity save(UserRegistrationDto registration) {

        validatePasswordCofirmation(registration);

        validateUsername(registration);

        RoleEntity roleEntity = getRoleEntityByName("ROLE_USER");

        UserEntity user = UserEntity.builder()
                .username(registration.getUsername())
                .password(passwordEncoder.encode(registration.getPassword()))
                .roles(Collections.singleton(roleEntity))
                .build();

        return userRepository.save(user);
    }

    private void validateUsername(UserRegistrationDto registration) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(registration.getUsername());
        if (byUsername.isPresent()) {
            throw new UsernameExistException();
        }
    }

    private void validatePasswordCofirmation(UserRegistrationDto registration) {
        boolean isConfirmationNotMatched = !registration.getPassword().equals(registration.getConfirmPassword());
        if (isConfirmationNotMatched) {
            throw new PasswordConfirmationNotMatchException();
        }
    }

    private RoleEntity getRoleEntityByName(String roleName) {
        Optional<RoleEntity> roleFindByName = roleRepository.findByName(roleName);

        RoleEntity roleEntity;

        if (roleFindByName.isPresent()) {
            roleEntity = roleFindByName.get();
        } else {
            roleEntity = new RoleEntity(roleName);
            roleRepository.save(roleEntity);
        }
        return roleEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        if (!byUsername.isPresent()) {
            throw new UsernameNotFoundException("Username does not exist");
        }

        UserEntity user = byUsername.get();

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<RoleEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
