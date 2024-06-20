package fmi.user_service.controller;

import fmi.user_service.dto.response.MessageDto;
import fmi.user_service.dto.user.UserSpecificsDto;
import fmi.user_service.exceptions.OperationNotPermittedException;
import fmi.user_service.exceptions.RegistrationFailedException;
import fmi.user_service.mapper.UserMapper;
import fmi.user_service.model.UserEntity;
import fmi.user_service.service.UserService;
import org.springframework.cloud.openfeign.FeignClient;
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
