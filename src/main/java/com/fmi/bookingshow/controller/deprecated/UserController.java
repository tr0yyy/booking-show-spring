package com.fmi.bookingshow.controller.deprecated;

import com.fmi.bookingshow.dto.response.MessageDto;
import com.fmi.bookingshow.dto.user.UserSpecificsDto;
import com.fmi.bookingshow.exceptions.OperationNotPermittedException;
import com.fmi.bookingshow.exceptions.RegistrationFailedException;
import com.fmi.bookingshow.mapper.UserMapper;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.service.deprecated.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;

//DEPRECATED @RestController
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    //DEPRECATED @GetMapping("/core/userspecifics/get")
    public UserSpecificsDto getUserSpecifics(Authentication authentication) {
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        return userService.getUserSpecifics(currentUser);
    }

    //DEPRECATED @PostMapping("/core/userspecifics/update")
    public UserSpecificsDto updateUserSpecifics(Authentication authentication, @RequestBody UserSpecificsDto userSpecificsDto) throws ParseException {
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        return userService.updateUserSpecifics(currentUser, userMapper.userSpecificsDtoToUserSpecificsEntity(userSpecificsDto));
    }

    //DEPRECATED @PostMapping("/admin/init")
    public MessageDto initAdmin() throws RegistrationFailedException, OperationNotPermittedException {
        UserEntity user = userService.getUserByUsername("admin");
        if (user != null) {
            return new MessageDto("Admin already initialized");
        }
        user = new UserEntity("admin", "admin", "admin@admin.com");
        userService.register(user);
        userService.registerAdmin(user);
        return new MessageDto("Admin initialized successfully - username: admin, password: admin");
    }
}
