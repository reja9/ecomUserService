package dev.reja.ecom.userService.repositories;

import dev.reja.ecom.userService.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Set<Role> findByIdIn(List<UUID> roleIds);
}
