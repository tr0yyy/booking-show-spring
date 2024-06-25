package com.fmi.bookingshow.microservices.user_service;

import com.fmi.bookingshow.dto.response.MessageDto;
import com.fmi.bookingshow.dto.response.ResponseDto;
import com.fmi.bookingshow.dto.user.OutputLoginDto;
import com.fmi.bookingshow.dto.user.UserDto;
import com.fmi.bookingshow.dto.user.UserSpecificsDto;
import com.fmi.bookingshow.mapper.UserMapper;
import com.fmi.bookingshow.microservices.GenericService;
import com.fmi.bookingshow.model.UserEntity;
import feign.Response;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService extends GenericService {

    private final UserServiceProxy userServiceProxy;
    private final UserMapper userMapper;

    public UserService(UserServiceProxy userServiceProxy, UserMapper userMapper) {
        this.userServiceProxy = userServiceProxy;
        this.userMapper = userMapper;
    }

    public MessageDto registerAccount(UserDto userDto) {
        return userServiceProxy.registerAccount(userDto);
    }

    public ResponseDto<OutputLoginDto> loginAccount(UserDto userDto) {
        return userServiceProxy.loginAccount(userDto);
    }

    public ResponseDto<UserSpecificsDto> getUserSpecifics(Authentication authentication) {
        return userServiceProxy.getUserSpecifics(((UserEntity) authentication.getPrincipal()).getUsername());
    }

    public ResponseDto<UserSpecificsDto> updateUserSpecifics(Authentication authentication, @RequestBody UserSpecificsDto userSpecificsDto) {
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        userSpecificsDto.setUsername(currentUser.getUsername());
        return userServiceProxy.updateUserSpecifics(userSpecificsDto);
    }

    public MessageDto initAdmin(){
        return userServiceProxy.initAdmin();
    }
}
