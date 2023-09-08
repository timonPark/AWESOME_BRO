package org.awesomeboro.awesome_bro.user;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.auth.AuthRepository;
import org.awesomeboro.awesome_bro.auth.Authority;
import org.awesomeboro.awesome_bro.auth.Token;
import org.awesomeboro.awesome_bro.security.TokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    /**
     * 회원가입
     * @param user
     * @return
     */
    @Override
    @Transactional
    public User signUp(User user){
        // 이메일 이미 존재하면 에러 처리
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        // 가입 안되어 있으면 진행
        // 1.권한정보 만들기
        Authority authority = authRepository.findByAuthorityName("ROLE_USER")
                .orElseGet(() -> {
                    Authority newAuthority = Authority.builder().authorityName("ROLE_USER").build();
                    return authRepository.save(newAuthority);
                });
        // 2. 유저 정보 만들기
        User userInfo = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(passwordEncoder.encode(user.getPassword()))
                .phoneNumber(user.getPhoneNumber())
                .loginType(user.getLoginType())
                .socialId(user.getSocialId())
                .profilePicture(user.getProfilePicture())
                .authorities(Collections.singleton(authority))
                .useYn("y")
                .build();

        return userRepository.save(userInfo);
    }

    public Token login(User user){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        return new Token(jwt);
    }



    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String email) {
        return userRepository.findOneWithAuthoritiesByEmail(email);
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByEmail);
    }

    @Override
    public User findUser(Long id){
        return userRepository.findById(id).orElseThrow();
    }
}
