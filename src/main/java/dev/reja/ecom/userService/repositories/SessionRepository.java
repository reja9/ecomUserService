package dev.reja.ecom.userService.repositories;

import dev.reja.ecom.userService.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    Optional<Session> findByTokenAndId(String token, UUID userId);
}
