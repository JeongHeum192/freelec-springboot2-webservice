package com.jhyeon.book.springboot.config.auth;

import com.jhyeon.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests() //URL별 권한 관리를 설정하는 옵션의 시작점
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll() // "/" 등 지정된 URL들은 전체 열람 권한
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // /api/v1/** 주소를 가진 API는 USER 권한을 가진 사람만 가능
                .anyRequest().authenticated() //설정된 값들 이외 나머지 URL들은 모두 인증된 사람들에게만 허용
                .and()
                .logout()
                .logoutSuccessUrl("/") //로그아웃 성공 시 "/" 주소로 이동
                .and()
                .oauth2Login()
                .userInfoEndpoint() //OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당
                .userService(customOAuth2UserService); //추가로 진행할 기능 명시
    }
}