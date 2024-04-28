package com.fmi.bookingshow.unit.service;

import com.fmi.bookingshow.component.JwtSecurity;
import com.fmi.bookingshow.exceptions.LoginFailedException;
import com.fmi.bookingshow.exceptions.RegistrationFailedException;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.model.UserSpecificsEntity;
import com.fmi.bookingshow.repository.UserRepository;
import com.fmi.bookingshow.repository.UserSpecificsRepository;
import com.fmi.bookingshow.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserSpecificsRepository userSpecificsRepository;

    @Mock
    private JwtSecurity jwtSecurity;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testValidGetUserByUsername() {
        UserEntity user = new UserEntity("user1", "pass", "user@example.com", null, null);
        Mockito.when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        UserEntity result = userService.getUserByUsername("user1");
        Assertions.assertEquals(user, result);
    }

    @Test
    public void testNullGetUserByUsername() {
        Mockito.when(userRepository.findByUsername("user2")).thenReturn(Optional.empty());
        UserEntity result = userService.getUserByUsername("user2");
        Assertions.assertNull(result);
    }

    @Test
    public void testSuccessRegistration() throws RegistrationFailedException {
        UserEntity newUser = new UserEntity("newUser", "password", "newUser@example.com", null, null);
        Mockito.when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        Mockito.when(userSpecificsRepository.save(any(UserSpecificsEntity.class))).thenReturn(new UserSpecificsEntity());
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        userService.register(newUser);
        Mockito.verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    public void testFailedRegistration() {
        UserEntity existingUser = new UserEntity("user1", "pass", "user@example.com", null, null);
        Mockito.when(userRepository.findByUsername("user1")).thenReturn(Optional.of(existingUser));
        Assertions.assertThrows(RegistrationFailedException.class, () -> userService.register(existingUser));
    }

    @Test
    public void testSuccessfulLogin() throws LoginFailedException {
        UserEntity user = new UserEntity("user1", "encodedPassword", "user@example.com", null, null);
        Mockito.when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        Mockito.when(jwtSecurity.generateToken(any(UserEntity.class))).thenReturn("token");
        Map<UserEntity, String> result = userService.login(new UserEntity("user1", "password", null, null, null));
        Assertions.assertEquals("token", result.get(user));
    }

    @Test
    public void testFailedLoginInvalidPassword() {
        UserEntity user = new UserEntity("user1", "encodedPassword", "user@example.com", null, null);
        Mockito.when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);
        Assertions.assertThrows(LoginFailedException.class, () -> userService.login(new UserEntity("user1", "wrongPassword", null, null, null)));
    }
}
