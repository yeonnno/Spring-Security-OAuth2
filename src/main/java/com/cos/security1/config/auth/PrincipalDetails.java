package com.cos.security1.config.auth;


import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/*
security가 /login 주소 요청이 오면 낚아채서 로그인을 진행
로그인 진행이 완료되면 security session을 만들어 줌 (Security ContextHolder)
오브젝트 -> Authentication 타입 객체
Authentication 안에 User 정보가 있어야 됨
User 오브젝트 타입 -> UserDetails 타입 객체
Security Session -> Authentication -> UserDetails(PrincipalDetails)
*/

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user; // 콤포지션
    private Map<String, Object> attributes;

    // 일반 로그인을 위한 생성자
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // OAuth 로그인을 위한 생성자
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // 해당 User의 권한을 return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // ex) 1년동안 회원이 로그인을 안하면 휴면 계정으로
        // 현재시간 - 로그인시간 => 1년을 초과하면 return false;
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
