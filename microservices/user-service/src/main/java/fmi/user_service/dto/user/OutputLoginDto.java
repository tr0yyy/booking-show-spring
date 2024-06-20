package fmi.user_service.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutputLoginDto extends UserDto {
    public String bearerToken;

    public OutputLoginDto(Long userId, String username, String email, String role, String bearerToken) {
        super(userId, username, email, role);
        this.bearerToken = bearerToken;
    }

    public OutputLoginDto() {
    }
}
