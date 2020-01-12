package com.lhmai.funnytube.service;

import com.lhmai.funnytube.FunnytubeApplication;
import com.lhmai.funnytube.domain.UserEntity;
import com.lhmai.funnytube.exception.BussinessException;
import com.lhmai.funnytube.exception.UserEntityNotFoundException;
import com.lhmai.funnytube.repository.UserRepository;
import com.lhmai.funnytube.service.dto.UserRegistrationDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FunnytubeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findByUsername_WithValidUsername() {
        when(userRepository.findByUsername(any()))
                .thenReturn(Optional.of(new UserEntity()));

        UserEntity userEntity = userService.findByUsername("username");

        Assert.assertNotNull(userEntity);
    }

    @Test(expected = UserEntityNotFoundException.class)
    public void findByUsername_NotExistUsername_ThrowException() {
        when(userRepository.findByUsername(any()))
                .thenReturn(Optional.empty());
        userService.findByUsername("username");
    }

    @Test
    public void save_WithValidUserInformation_ReturnSavedUserEntity() {
        when(userRepository.save(any()))
                .thenReturn(new UserEntity());

        UserRegistrationDto user = UserRegistrationDto.builder()
                .username("myusername")
                .password("mypassword")
                .confirmPassword("mypassword")
                .build();

        UserEntity userEntity = userService.save(user);

        Assert.assertNotNull(userEntity);
    }


    @Test(expected = BussinessException.class)
    public void save_ConfirmationPasswordNotMatched_ThrowException() {
        UserRegistrationDto user = UserRegistrationDto.builder()
                .username("myusername")
                .password("mypassword")
                .confirmPassword("mypassword1")
                .build();
        UserEntity userEntity = userService.save(user);
    }

    @Test
    public void loadUserByUsername_WithValidUsername_ReturnUserDetails() {
        when(userRepository.findByUsername(any()))
                .thenReturn(Optional.of(
                        UserEntity.builder()
                                .username("username")
                                .password("pasword")
                                .roles(new HashSet<>())
                                .build()
                ));

        UserDetails userDetails = userService.loadUserByUsername("username");

        Assert.assertNotNull(userDetails);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_NotExistUsername_ThrowException() {
        when(userRepository.findByUsername(any()))
                .thenReturn(Optional.empty());

        userService.loadUserByUsername("username");

    }
}