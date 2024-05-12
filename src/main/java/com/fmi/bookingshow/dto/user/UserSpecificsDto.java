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

    public UserSpecificsDto(Long userId, String username, String email, String role, String dateOfBirth, String bio, String preferences, String registrationDate) {
        super(userId, username, email, role);
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.preferences = preferences;
        this.registrationDate = registrationDate;
    }
}
