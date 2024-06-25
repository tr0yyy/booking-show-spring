package com.fmi.bookingshow.microservices.user_service;

import com.fmi.bookingshow.dto.response.MessageDto;
import com.fmi.bookingshow.dto.response.ResponseDto;
import com.fmi.bookingshow.dto.user.OutputLoginDto;
import com.fmi.bookingshow.dto.user.UserDto;
import com.fmi.bookingshow.dto.user.UserSpecificsDto;
import com.fmi.bookingshow.microservices.GenericServiceProxy;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceProxy extends GenericServiceProxy {
    @GetMapping("/core/userspecifics/get?username={username}")
    ResponseDto<UserSpecificsDto> getUserSpecifics(@Param("username") String username);

    @PostMapping("/core/userspecifics/update")
    ResponseDto<UserSpecificsDto> updateUserSpecifics(@RequestBody UserSpecificsDto userSpecificsDto);

    @PostMapping("/admin/init")
    MessageDto initAdmin();

    @PostMapping("/auth/register")
    MessageDto registerAccount(@RequestBody UserDto userDto);

    @PostMapping("/auth/login")
    ResponseDto<OutputLoginDto> loginAccount(@RequestBody UserDto userDto);
}
