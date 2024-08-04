package dev.reja.ecom.userService.controllers;

import dev.reja.ecom.userService.dtos.SendUserRolesDto;
import dev.reja.ecom.userService.models.Role;
import dev.reja.ecom.userService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable ("id")UUID userId){
        return  ResponseEntity.ok(userService.findById(userId));
    }

    @PostMapping("/roles/{id}")
    public ResponseEntity setUserRoles(@PathVariable("id") UUID userId, @RequestBody SendUserRolesDto sendUserRolesDto){

        return ResponseEntity.ok(userService.setUserRoles(userId, sendUserRolesDto));
    }
    @GetMapping
    public ResponseEntity getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/token")
    public ResponseEntity getUserBytoken(@RequestParam ("token") String token){
        return ResponseEntity.ok(userService.getUserDetailsByToken(token));
    }
}
