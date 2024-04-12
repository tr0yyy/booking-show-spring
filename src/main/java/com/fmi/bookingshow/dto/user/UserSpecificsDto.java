package com.fmi.bookingshow.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSpecificsDto extends UserDto {
    public String dateOfBirth;
    public String bio;
    public String preferences;
    public String registrationDate;
}
