package com.fmi.bookingshow.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    public Long userId;
    @NotNull
    @NotBlank
    public String username;
    public String password;
    @NotNull
    @NotBlank
    public String email;
    public String role;

    public UserDto() {
    }

    public UserDto(Long userId, String username, String password, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public UserDto(Long userId, String username, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
