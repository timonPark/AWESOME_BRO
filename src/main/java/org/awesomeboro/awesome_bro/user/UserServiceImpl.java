package org.awesomeboro.awesome_bro.user;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.auth.AuthRepository;
import org.awesomeboro.awesome_bro.auth.Authority;
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

    @Override
    @Transactional
    public User signUp(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        // 가입 안되어 있으면 진행
        // 1.권한정보 만들기
//        Authority authority = Authority.builder()
//                .authorityName("ROLE_USER")
//                .build();
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
