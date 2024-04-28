package com.fmi.bookingshow.controller;

import com.fmi.bookingshow.dto.response.MessageDto;
import com.fmi.bookingshow.dto.response.ResponseDto;
import com.fmi.bookingshow.dto.user.OutputLoginDto;
import com.fmi.bookingshow.dto.user.UserDto;
import com.fmi.bookingshow.exceptions.LoginFailedException;
import com.fmi.bookingshow.exceptions.RegistrationFailedException;
import com.fmi.bookingshow.mapper.UserMapper;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/auth/register")
    public MessageDto registerAccount(@RequestBody UserDto userDto) throws RegistrationFailedException {
        UserEntity userEntity = userMapper.userDtoToUserEntity(userDto);
        userService.register(userEntity);
        return new MessageDto("User registered successfully");
    }

    @PostMapping("/auth/login")
    public OutputLoginDto loginAccount(@RequestBody UserDto userDto) throws LoginFailedException {
        UserEntity userEntity = userMapper.userDtoToUserEntity(userDto);
        Map.Entry<UserEntity, String> userEntityToken = userService.login(userEntity).entrySet().iterator().next();
        UserEntity outputUserEntity = userEntityToken.getKey();
        String outputBearerToken = userEntityToken.getValue();
        return new OutputLoginDto(
                outputUserEntity.getUserId(),
                outputUserEntity.getUsername(),
                outputUserEntity.getEmail(),
                outputUserEntity.getRole(),
                outputBearerToken
        );
    }
}
