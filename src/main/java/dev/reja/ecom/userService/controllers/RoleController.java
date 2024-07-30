package dev.reja.ecom.userService.controllers;

import dev.reja.ecom.userService.models.Role;
import dev.reja.ecom.userService.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping
    public ResponseEntity createRole(@RequestBody Role role){

        return ResponseEntity.ok(roleService.createRole(role));
    }
}
