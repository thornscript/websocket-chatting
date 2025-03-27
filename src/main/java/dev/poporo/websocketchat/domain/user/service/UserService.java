package dev.poporo.websocketchat.domain.user.service;

import dev.poporo.websocketchat.common.exception.DuplicateResourceException;
import dev.poporo.websocketchat.common.exception.ErrorCode;
import dev.poporo.websocketchat.domain.user.dto.UserCreateRequest;
import dev.poporo.websocketchat.domain.user.entity.User;
import dev.poporo.websocketchat.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public Long createUser(UserCreateRequest request) {
        validateUserDuplicate(request.username());

        User user = User.builder()
                .username(request.username())
                .build();
        user.encodePassword(passwordEncoder, request.password());
        return userJpaRepository.save(user).getId();
    }

    private void validateUserDuplicate(String username) {
        if (userJpaRepository.existsByUsername(username)) {
            throw new DuplicateResourceException(ErrorCode.DUPLICATE_USERNAME);
        }
    }
}
