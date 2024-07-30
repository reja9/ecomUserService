package dev.reja.ecom.userService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {

    private String userName;
    private String name;
    private String email;
    private String contactNo;
    private String password;
}
