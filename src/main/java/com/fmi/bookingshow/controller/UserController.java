package com.fmi.bookingshow.controller;

import com.fmi.bookingshow.dto.user.UserSpecificsDto;
import com.fmi.bookingshow.mapper.UserMapper;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/core/userspecifics/get")
    public UserSpecificsDto getUserSpecifics(Authentication authentication) {
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        return userService.getUserSpecifics(currentUser);
    }

    @PostMapping("/core/userspecifics/update")
    public UserSpecificsDto updateUserSpecifics(Authentication authentication, @RequestBody UserSpecificsDto userSpecificsDto) throws ParseException {
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        return userService.updateUserSpecifics(currentUser, userMapper.userSpecificsDtoToUserSpecificsEntity(userSpecificsDto));
    }
}
