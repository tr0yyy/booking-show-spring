package com.fmi.bookingshow.integration.service;

import com.fmi.bookingshow.component.JwtSecurity;
import com.fmi.bookingshow.exceptions.LoginFailedException;
import com.fmi.bookingshow.exceptions.RegistrationFailedException;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.model.UserSpecificsEntity;
import com.fmi.bookingshow.repository.UserRepository;
import com.fmi.bookingshow.repository.UserSpecificsRepository;
import com.fmi.bookingshow.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void purgeDatabase() {
        userRepository.deleteAll();
    }

    @Test
    public void testSuccessRegistrationAndLogin() throws RegistrationFailedException, LoginFailedException {
        UserEntity newUser = new UserEntity("newUser", "password", "newUser@example.com", null, null);
        userService.register(newUser);
        Map<UserEntity, String> userEntityToken = userService.login(newUser);
        Map.Entry<UserEntity, String> userEntityTokenEntry = userEntityToken.entrySet().iterator().next();
        Assertions.assertEquals(newUser.getUsername(), userEntityTokenEntry.getKey().getUsername());
    }
}
