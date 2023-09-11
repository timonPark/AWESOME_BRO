package org.awesomeboro.awesome_bro.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.exception.GeneralException;
import org.awesomeboro.awesome_bro.user.User;
import org.awesomeboro.awesome_bro.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.awesomeboro.awesome_bro.constant.ErrorCode.LOGIN_ERROR;
import static org.awesomeboro.awesome_bro.constant.LoginType.NORMAL;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String userEmail) {
        return userRepository.findOneWithUserAuthoritiesByEmail(userEmail)
                .map(this::CheckUserAuth)
                .orElseThrow(() -> new GeneralException(LOGIN_ERROR));
    }

    private org.springframework.security.core.userdetails.User CheckUserAuth(User user) {
        List<GrantedAuthority> grantedAuthorities = user.getUserAuthorities().stream()
                .map(userAuthority ->
                        new SimpleGrantedAuthority(
                                userAuthority
                                        .getAuthority()
                                        .getName()
                        ))
                .collect(Collectors.toList());

        if (user.getLoginType() != NORMAL.getName()) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    grantedAuthorities);
        } else {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    grantedAuthorities);
        }

    }
}
