package fmi.user_service.mapper;

import fmi.user_service.dto.user.OutputLoginDto;
import fmi.user_service.dto.user.UserDto;
import fmi.user_service.dto.user.UserSpecificsDto;
import fmi.user_service.model.UserEntity;
import fmi.user_service.model.UserSpecificsEntity;
import fmi.user_service.utils.DateUtils;
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
                userEntity.getUserSpecifics().getBirthday() == null ? null : userEntity.getUserSpecifics().getBirthday().toString(),
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
