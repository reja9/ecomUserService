package dev.reja.ecom.userService.services;

import dev.reja.ecom.userService.dtos.SendUserRolesDto;
import dev.reja.ecom.userService.exceptions.SessionNotFoundException;
import dev.reja.ecom.userService.exceptions.UserNotFoundException;
import dev.reja.ecom.userService.models.Role;
import dev.reja.ecom.userService.models.Session;
import dev.reja.ecom.userService.models.SessionStatus;
import dev.reja.ecom.userService.models.User;
import dev.reja.ecom.userService.repositories.RoleRepository;
import dev.reja.ecom.userService.repositories.SessionRepository;
import dev.reja.ecom.userService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Override
    public User findById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                ()-> new UserNotFoundException("UserId "+userId+" is not in the db")
        );
    }

    @Override
    public User setUserRoles(UUID userId, SendUserRolesDto sendUserRolesDto) {
        User user=userRepository.findById(userId).orElseThrow(
                ()-> new UserNotFoundException("UserId "+userId+" is not in the db")
        );
        Set<Role> roles=roleRepository.findByIdIn(sendUserRolesDto.getRoleIds());
        user.setRoles(roles);
        return user;

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users=userRepository.findAll();
        return users;
    }

    @Override
    public User getUserDetailsByToken(String token) {
        Session session=sessionRepository.findByTokenAndSessionStatus(token, SessionStatus.ACTIVE).orElseThrow(
                ()-> new SessionNotFoundException("The user is no=t valid")
        );
        User user=session.getUser();

        return user;
    }
}
