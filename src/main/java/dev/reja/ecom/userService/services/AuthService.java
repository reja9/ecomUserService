package dev.reja.ecom.userService.services;

import dev.reja.ecom.userService.dtos.LoginResponseDto;
import dev.reja.ecom.userService.dtos.SignUpRequestDto;
import dev.reja.ecom.userService.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public interface AuthService {


    public LoginResponseDto logIn(String email, String password);
    public User signUp(SignUpRequestDto signUpRequestDto);
    public String logout(String token, UUID userId);
}
