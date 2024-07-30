package dev.reja.ecom.userService.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LoginResponseDto {

    private String token;
    private UUID userId;
    private String email;
}
