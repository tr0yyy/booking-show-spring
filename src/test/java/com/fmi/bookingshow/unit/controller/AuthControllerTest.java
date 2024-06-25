package com.fmi.bookingshow.unit.controller;

import com.fmi.bookingshow.controller.AuthController;
import com.fmi.bookingshow.dto.user.OutputLoginDto;
import com.fmi.bookingshow.dto.user.UserDto;
import com.fmi.bookingshow.exceptions.LoginFailedException;
import com.fmi.bookingshow.mapper.UserMapper;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.service.deprecated.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("H2-Testing")
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;


    @Test
    public void testLoginAccountSuccess() throws LoginFailedException {
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();
        String token = "Bearer token";

        Map<UserEntity, String> result = createLoginResponse(userEntity, token);
        when(userService.login(userEntity)).thenReturn(result);
        when(userMapper.userDtoToUserEntity(userDto)).thenReturn(userEntity);
        OutputLoginDto response = authController.loginAccount(userDto);

        assertEquals(userEntity.getUserId(), response.getUserId());
    }

    private Map<UserEntity, String> createLoginResponse(UserEntity userEntity, String token) {
        Map<UserEntity, String> map = new HashMap<>();
        map.put(userEntity, token);
        return map;
    }
}
