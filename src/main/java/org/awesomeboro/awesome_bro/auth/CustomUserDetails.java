package org.awesomeboro.awesome_bro.auth;

import lombok.*;
import org.awesomeboro.awesome_bro.dto.user.CustomUserDetailDto;
import org.awesomeboro.awesome_bro.userAuthority.UserAuthority;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;


@Getter
public class CustomUserDetails implements UserDetails{
    private final Long id;
    private final String name;
    private final String nickname;
    private final String password;
    private final String phoneNumber;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(CustomUserDetailDto userDto) {
        this.id=userDto.getId();
        this.email=userDto.getEmail();
        this.password=userDto.getPassword();
        this.name=userDto.getName();
        this.nickname=userDto.getNickname();
        this.phoneNumber=userDto.getPhoneNumber();
        Collection<? extends GrantedAuthority> roles = Arrays.stream(userDto.getUserRoles().toArray()).map(r -> new SimpleGrantedAuthority(((UserAuthority) r).getAuthority().getName())).toList();
        this.authorities=roles;

    }

    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
        return this.email;
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
        return true;
    }


    public static CustomUserDetails get() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!isAuthenticated(authentication)) {
            throw new AuthenticationCredentialsNotFoundException("Not Authenticate");
        }
        return (CustomUserDetails) authentication.getPrincipal();
    }

    /**
     * 미인증 여부.
     */
    public static boolean isAuthenticated(final Authentication authentication) {
        return authentication != null
                && authentication.isAuthenticated() // 인증되지 않은경우.
                && authentication.getPrincipal() != null
                && authentication.getPrincipal() instanceof CustomUserDetails;
    }
}
