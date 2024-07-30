package dev.reja.ecom.userService.services;

import dev.reja.ecom.userService.models.Role;
import dev.reja.ecom.userService.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Role createRole(Role role) {
        Role response=roleRepository.save(role);
        return response;
    }
}
