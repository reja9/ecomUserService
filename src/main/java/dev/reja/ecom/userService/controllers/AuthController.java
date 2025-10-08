package dev.reja.ecom.userService.controllers;

import dev.reja.ecom.userService.dtos.SignUpRequestDto;
import dev.reja.ecom.userService.models.User;
import dev.reja.ecom.userService.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return ok(authService.logIn(email, password));

    }

    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return ok(authService.signUp(signUpRequestDto));

    }

    @PostMapping("/logout/{id}")
    public ResponseEntity logout(@PathVariable("id") UUID userId, @RequestHeader("Authorization") String token) {
        return ok(authService.logout(token, userId));
    }

    @GetMapping("userName/availavility")
    public ResponseEntity getAvailableUserName(String userName) {

        return ok(authService.getUserNameAvailability(userName));
    }

    @GetMapping("/token")
    public ResponseEntity getUserBytoken(@RequestHeader("Authorization") String token) {
        return ok(authService.userDetailsByToken(token));
    }

    @GetMapping("/getAccesTokenByRefreshToken")
    public ResponseEntity getAccessToken(@RequestHeader("Authorization") String refreshToken) {
        return ok(authService.getAccessToken(refreshToken));
    }
}


