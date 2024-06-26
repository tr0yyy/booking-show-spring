package fmi.user_service.controller;

import fmi.user_service.dto.response.MessageDto;
import fmi.user_service.dto.user.UserDto;
import fmi.user_service.dto.user.UserSpecificsDto;
import fmi.user_service.exceptions.OperationNotPermittedException;
import fmi.user_service.exceptions.RegistrationFailedException;
import fmi.user_service.mapper.UserMapper;
import fmi.user_service.model.UserEntity;
import fmi.user_service.service.UserService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/core/user/get")
    public Boolean userExists(Authentication authentication) {
        return authentication.getPrincipal() != null;
    }

    @PostMapping("/core/userspecifics/get")
    public UserSpecificsDto getUserSpecifics(Authentication authentication) throws OperationNotPermittedException {
        return userService.getUserSpecifics(((UserEntity) authentication.getPrincipal()).getUsername());
    }

    @PostMapping("/core/userspecifics/update")
    public UserSpecificsDto updateUserSpecifics(@RequestBody UserSpecificsDto userSpecificsDto) throws ParseException {
        UserEntity currentUser = userService.getUserByUsername(userSpecificsDto.getUsername());
        return userService.updateUserSpecifics(currentUser, userMapper.userSpecificsDtoToUserSpecificsEntity(userSpecificsDto));
    }

    @PostMapping("/admin/init")
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
