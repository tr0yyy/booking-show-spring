package com.fmi.bookingshow.mapper;

import com.fmi.bookingshow.dto.user.OutputLoginDto;
import com.fmi.bookingshow.dto.user.UserDto;
import com.fmi.bookingshow.dto.user.UserSpecificsDto;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.model.UserSpecificsEntity;
import com.fmi.bookingshow.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;

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

    public UserSpecificsDto userEntitytoUserSpecificsDto(UserEntity userEntity) {
        return new UserSpecificsDto(
                userEntity.getUserId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getRole(),
                userEntity.getUserSpecifics().getBirthday().toString(),
                userEntity.getUserSpecifics().getBio(),
                userEntity.getUserSpecifics().getPreferences(),
                userEntity.getUserSpecifics().getRegistrationDate().toString()
        );
    }

    public UserSpecificsEntity userSpecificsDtoToUserSpecificsEntity(UserSpecificsDto userSpecificsDto) throws ParseException {
        return new UserSpecificsEntity(
                DateUtils.parseDate(userSpecificsDto.dateOfBirth),
                userSpecificsDto.bio,
                userSpecificsDto.preferences
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
