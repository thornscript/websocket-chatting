package dev.poporo.websocketchat.domain.user.controller;

import dev.poporo.websocketchat.domain.user.dto.UserCreateRequest;
import dev.poporo.websocketchat.domain.user.service.UserService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserCreateRequest request) {
        Long userId = userService.createUser(request);
        return ResponseEntity.created(URI.create("/api/v1/users/" + userId)).build();
    }
}
