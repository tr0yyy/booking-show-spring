package com.fmi.bookingshow.unit.controller;

import com.fmi.bookingshow.controller.AuthController;
import com.fmi.bookingshow.dto.response.MessageDto;
import com.fmi.bookingshow.dto.user.UserDto;
import com.fmi.bookingshow.exceptions.LoginFailedException;
import com.fmi.bookingshow.exceptions.RegistrationFailedException;
import com.fmi.bookingshow.mapper.UserMapper;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterAccount_Success() throws RegistrationFailedException {
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();

        when(userMapper.userDtoToUserEntity(userDto)).thenReturn(userEntity);
        when(userService.register(userEntity)).thenReturn(userEntity);

        ResponseEntity<MessageDto> response = authController.registerAccount(userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody().getMessage());
        verify(userMapper).userDtoToUserEntity(userDto);
        verify(userService).register(userEntity);
    }

    @Test
    void testLoginAccount_Success() throws LoginFailedException {
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();
        String token = "Bearer token";

        when(userMapper.userDtoToUserEntity(userDto)).thenReturn(userEntity);
        when(userService.login(userEntity)).thenReturn(createLoginResponse(userEntity, token));

        ResponseEntity<?> response = authController.loginAccount(userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userMapper).userDtoToUserEntity(userDto);
        verify(userService).login(userEntity);
    }

    private Map.Entry<UserEntity, String> createLoginResponse(UserEntity userEntity, String token) {
        Map.Entry<UserEntity, String> entry = new HashMap<>();
        entry.put(userEntity, token);
        return entry;
    }
}
