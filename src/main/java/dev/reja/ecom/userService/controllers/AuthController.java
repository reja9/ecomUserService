package dev.reja.ecom.userService.controllers;

import dev.reja.ecom.userService.dtos.SignUpRequestDto;
import dev.reja.ecom.userService.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(String email, String password){
        return ResponseEntity.ok(authService.logIn(email,password));

    }
    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return ResponseEntity.ok(authService.signUp(signUpRequestDto));

    }

    @PostMapping("/logout/{id}")
    public ResponseEntity logout(@PathVariable ("id") UUID userId, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(authService.logout(token,userId));
    }


}
