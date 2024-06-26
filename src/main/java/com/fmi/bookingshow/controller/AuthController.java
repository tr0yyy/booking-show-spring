package com.fmi.bookingshow.controller;

import com.fmi.bookingshow.dto.response.MessageDto;
import com.fmi.bookingshow.dto.response.ResponseDto;
import com.fmi.bookingshow.dto.user.OutputLoginDto;
import com.fmi.bookingshow.dto.user.UserDto;
import com.fmi.bookingshow.microservices.user_service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public MessageDto registerAccount(@RequestBody UserDto userDto) {
        return userService.registerAccount(userDto);
    }

    @PostMapping("/auth/login")
    public ResponseDto<OutputLoginDto> loginAccount(@RequestBody UserDto userDto) {
        return userService.loginAccount(userDto);
    }
}
