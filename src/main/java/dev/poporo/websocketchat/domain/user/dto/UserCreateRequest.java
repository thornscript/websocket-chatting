package dev.poporo.websocketchat.domain.user.dto;

public record UserCreateRequest(
        String username,
        String password
) {
}
