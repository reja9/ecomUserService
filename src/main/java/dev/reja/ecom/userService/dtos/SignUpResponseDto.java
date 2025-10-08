package dev.reja.ecom.userService.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SignUpResponseDto {
    private String token;
    private String refreshToken;
    private UUID userId;
    private String email;
    private String name;
    private String userName;
}
