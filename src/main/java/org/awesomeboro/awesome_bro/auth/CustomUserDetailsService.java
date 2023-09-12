package org.awesomeboro.awesome_bro.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.exception.PasswordException;
import org.awesomeboro.awesome_bro.exception.UserNotFoundException;
import org.awesomeboro.awesome_bro.user.User;
import org.awesomeboro.awesome_bro.user.UserRepository;
import org.awesomeboro.awesome_bro.user.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.awesomeboro.awesome_bro.constant.ErrorCode.*;
import static org.awesomeboro.awesome_bro.constant.LoginType.NORMAL;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String userEmail) {
       org.springframework.security.core.userdetails.User validatedUser = userRepository.findByEmail(userEmail).map(this::checkUserAuth)
               .orElseThrow(() -> new UserNotFoundException(UNDEFINED_EMAIL));
       Optional<User> user = userService.getUserWithAuthorities(userEmail);
        if (user.isPresent() && "n".equals(user.get().getUseYn())) {
            throw new UserNotFoundException(DELETED_USER);
        }
        return validatedUser;
    }

    private org.springframework.security.core.userdetails.User checkUserAuth(User user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (user.getUserAuthorities() != null) {
            grantedAuthorities = user.getUserAuthorities().stream()
                    .map(userAuthority -> new SimpleGrantedAuthority(userAuthority.getAuthority().getName()))
                    .collect(Collectors.toList());
        }
            return  new org.springframework.security.core.userdetails.User(user.getEmail(),
                    Objects.equals(user.getLoginType(), NORMAL.getName()) ? user.getPassword() : passwordEncoder.encode(user.getSocialId()),
                    grantedAuthorities);

    }
}
