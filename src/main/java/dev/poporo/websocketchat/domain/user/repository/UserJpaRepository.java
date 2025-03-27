package dev.poporo.websocketchat.domain.user.repository;

import dev.poporo.websocketchat.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
}
