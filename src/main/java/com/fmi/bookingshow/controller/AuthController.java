package com.fmi.bookingshow.controller;

import com.fmi.bookingshow.component.ApiFactory;
import com.fmi.bookingshow.dto.response.ResponseDto;
import com.fmi.bookingshow.dto.user.OutputLoginDto;
import com.fmi.bookingshow.dto.user.UserDto;
import com.fmi.bookingshow.exceptions.LoginFailedException;
import com.fmi.bookingshow.mapper.UserMapper;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ApiFactory apiFactory;

    public AuthController(UserService userService, UserMapper userMapper, ApiFactory apiFactory) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.apiFactory = apiFactory;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ResponseDto<String>> registerAccount(@RequestBody UserDto userDto) {
        return apiFactory.create(() -> {
            UserEntity userEntity = userMapper.userDtoToUserEntity(userDto);
            userService.register(userEntity);
            return "Registered successfully";
        }, LoginFailedException.class);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResponseDto<OutputLoginDto>> loginAccount(@RequestBody UserDto userDto) {
        return apiFactory.create(() -> {
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
        }, LoginFailedException.class);
    }
}
