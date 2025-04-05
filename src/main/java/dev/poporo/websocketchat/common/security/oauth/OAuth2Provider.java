package dev.poporo.websocketchat.common.security.oauth;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OAuth2Provider {

    LOCAL("local"),
    GOOGLE("google"),
    KAKAO("kakao");

    private final String providerId;
}
