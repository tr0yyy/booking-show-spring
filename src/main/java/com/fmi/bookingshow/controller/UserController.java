package com.fmi.bookingshow.controller;

import com.fmi.bookingshow.dto.response.MessageDto;
import com.fmi.bookingshow.dto.response.ResponseDto;
import com.fmi.bookingshow.dto.user.UserSpecificsDto;
import com.fmi.bookingshow.microservices.user_service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/core/userspecifics/get")
    public ResponseDto<UserSpecificsDto> getUserSpecifics(Authentication authentication) {
        return userService.getUserSpecifics(authentication);
    }

    @PostMapping("/core/userspecifics/update")
    public ResponseDto<UserSpecificsDto> updateUserSpecifics(Authentication authentication, @RequestBody UserSpecificsDto userSpecificsDto) {
        return userService.updateUserSpecifics(authentication, userSpecificsDto);
    }

    @PostMapping("/admin/init")
    public MessageDto initAdmin() {
        return userService.initAdmin();
    }
}
