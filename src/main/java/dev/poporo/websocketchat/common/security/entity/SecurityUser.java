package dev.poporo.websocketchat.common.security.entity;

import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class SecurityUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;

    @Builder
    public SecurityUser(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO: 현재는 권한이 비어있지만, 추후 권한 관련 전용 브랜치에서 구현 예정
        // 주의: 빈 권한 목록은 Spring Security의 권한 기반 접근 제어를 무력화시킬 수 있음
        return List.of();
    }
}
