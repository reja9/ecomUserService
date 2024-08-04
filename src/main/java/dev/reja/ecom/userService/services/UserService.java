package dev.reja.ecom.userService.services;

import dev.reja.ecom.userService.dtos.SendUserRolesDto;
import dev.reja.ecom.userService.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public User findById(UUID userId);
    public User setUserRoles(UUID userId, SendUserRolesDto sendUserRolesDto);

    public List<User> getAllUsers();
    public User getUserDetailsByToken(String token);
}
