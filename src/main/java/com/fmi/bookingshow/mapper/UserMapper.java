package com.fmi.bookingshow.mapper;

import com.fmi.bookingshow.dto.user.OutputLoginDto;
import com.fmi.bookingshow.dto.user.UserDto;
import com.fmi.bookingshow.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity userDtoToUserEntity(UserDto userDto) {
        return new UserEntity(
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getEmail()
        );
    }

    public UserDto userEntitytoUserDto(UserEntity userEntity) {
        return new UserDto(
                userEntity.getUserId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getRole()
        );
    }

    public OutputLoginDto userEntityAndTokenToOutputLogin(UserEntity userEntity, String bearerToken) {;
        return new OutputLoginDto(
                userEntity.getUserId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getRole(),
                bearerToken
        );
    }
}
