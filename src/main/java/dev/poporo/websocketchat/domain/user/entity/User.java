package dev.poporo.websocketchat.domain.user.entity;

import dev.poporo.websocketchat.common.BaseEntity;
import dev.poporo.websocketchat.common.security.oauth.OAuth2Provider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;

    private String profileImageUrl;

    @Builder
    public User(String username, String password, OAuth2Provider provider, String profileImageUrl) {
        this.username = username;
        this.password = password;
        this.provider = provider;
        this.profileImageUrl = profileImageUrl;
    }

    public void encodePassword(PasswordEncoder passwordEncoder, String rawPassword) {
        this.password = passwordEncoder.encode(rawPassword);
    }
}
