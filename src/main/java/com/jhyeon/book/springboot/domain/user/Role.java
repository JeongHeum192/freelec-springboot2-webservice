package com.jhyeon.book.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"), //스프링 시큐리티에서 권한 코드는 항상 ROLE_이 있어야된다.
    USER("ROLE_USER","일반 사용자");

    private final String key;
    private final String title;

}
