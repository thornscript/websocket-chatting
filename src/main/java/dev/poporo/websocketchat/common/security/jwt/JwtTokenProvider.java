package dev.poporo.websocketchat.common.security.jwt;

import dev.poporo.websocketchat.common.exception.ErrorCode;
import dev.poporo.websocketchat.common.exception.SecurityException;
import dev.poporo.websocketchat.common.security.entity.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID_KEY = "id";

    private final String BASE64_SECRET;
    private final Long ACCESS_TOKEN_EXPIRES_IN_MS;
    private final Long REFRESH_TOKEN_EXPIRES_IN_MS;

    private SecretKey key;

    public JwtTokenProvider(
            @Value("${app.security.jwt.secret-key}") String base64Secret,
            @Value("${app.security.jwt.access-token.validity-in-ms}") long accessTokenValidityInMs,
            @Value("${app.security.jwt.refresh-token.validity-in-ms}") long refreshTokenValidityInMs
    ) {
        this.BASE64_SECRET = base64Secret;
        this.ACCESS_TOKEN_EXPIRES_IN_MS = accessTokenValidityInMs;
        this.REFRESH_TOKEN_EXPIRES_IN_MS = refreshTokenValidityInMs;
    }

    @PostConstruct
    public void init() {
        byte[] secretKeyBytes = Decoders.BASE64.decode(BASE64_SECRET);
        this.key = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public String createAccessToken(SecurityUser user) {
        return createToken(user, ACCESS_TOKEN_EXPIRES_IN_MS);
    }

    public String createRefreshToken(SecurityUser user) {
        return createToken(user, REFRESH_TOKEN_EXPIRES_IN_MS);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getAllClaimsFromToken(token).getPayload();
        Collection<? extends GrantedAuthority> authorities = extractAuthorities(claims);
        SecurityUser principal = new SecurityUser(claims.get(USER_ID_KEY, Long.class), claims.getSubject(), "");
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public void validateToken(String token) throws SecurityException {
        try {
            getAllClaimsFromToken(token);
        } catch (ExpiredJwtException ex) {
            throw new SecurityException(ErrorCode.EXPIRED_TOKEN_EXCEPTION);
        } catch (JwtException ex) {
            throw new SecurityException(ErrorCode.INVALID_TOKEN_EXCEPTION);
        }
    }

    public boolean isValidToken(String token) {
        try {
            validateToken(token);
            return true;
        } catch (SecurityException ex) {
            log.debug("Invalid token: {}", ex.getMessage());
            return false;
        }
    }

    private String createToken(SecurityUser user, long expirationTime) {
        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .claim(USER_ID_KEY, user.getId())
                .claim(AUTHORITIES_KEY, authorities)
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SIG.HS512)
                .compact();
    }

    /**
     * 토큰에서 모든 클레임 정보를 추출합니다.
     * @param token JWT 토큰
     * @return 토큰에 포함된 클레임 정보
     * @throws JwtException 토큰 파싱 중 오류 발생 시
     */
    private Jws<Claims> getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }

    private Collection<SimpleGrantedAuthority> extractAuthorities(Claims claims) {
        return Arrays.stream(Optional.ofNullable(claims.get(AUTHORITIES_KEY))
                        .map(Object::toString)
                        .orElse("")
                        .split(","))
                .map(String::trim)
                .filter(auth -> !auth.isEmpty())
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
