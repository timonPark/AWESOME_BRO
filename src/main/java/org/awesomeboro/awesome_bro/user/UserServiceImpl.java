package org.awesomeboro.awesome_bro.user;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.dto.user.SocialLoginUserDto;
import org.awesomeboro.awesome_bro.auth.AuthServiceImpl;
import org.awesomeboro.awesome_bro.auth.AuthRepository;
import org.awesomeboro.awesome_bro.auth.Authority;
import org.awesomeboro.awesome_bro.dto.user.TokenDto;
import org.awesomeboro.awesome_bro.dto.user.UserLoginDto;
import org.awesomeboro.awesome_bro.dto.user.UserSignUpDto;
import org.awesomeboro.awesome_bro.utils.SecurityUtil;
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
    private final AuthServiceImpl authServiceImpl;

    /**
     * 회원가입
     * @param user
     * @return
     */
    @Override
    @Transactional
    public User createUser(UserSignUpDto user){
        // 1. 이메일 이미 존재하면 에러 처리
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        // 2. 비밀번호 자리수 8자리 이하 에러 처리
        if(user.getPassword().length() < 8){
            throw new RuntimeException("비밀번호는 8자리 이상이어야 합니다.");
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

    /**
     * 로그인
     * @param user
     * @return
     */
    public TokenDto login(UserLoginDto user){
        // 1. 이메일, 비밀번호가 없으면 에러 처리
        if(user.getEmail().length() < 1 || user.getPassword().length() < 1){
            throw new RuntimeException("이메일과 비밀번호를 입력해주세요.");
        }
        // 2. 가입되지 않은 이메일이면 에러 처리
        if(userRepository.findByEmail(user.getEmail()).isEmpty()){
            throw new RuntimeException("가입되지 않은 이메일입니다.");
        }
        // 3. 탈퇴한 회원이면 에러 처리
        if(userRepository.findByEmail(user.getEmail()).get().getUseYn().equals("n")){
            throw new RuntimeException("탈퇴한 회원입니다.");
        }
        // 4. 비밀번호가 일치하지 않으면 에러 처리
        if(!passwordEncoder.matches(user.getPassword(),userRepository.findByEmail(user.getEmail()).get().getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return authServiceImpl.getToken(user);
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

    /**
     * 소셜 로그인
     * @param user
     * @return
     */
    @Override
//    @Transactional
    public TokenDto socialLogin(final SocialLoginUserDto user) {
        return socialLoginProgress(getSocialLoginUser(user), user);
    }

    private TokenDto socialLoginProgress(final User user, SocialLoginUserDto socialLoginUserDto) {
        User socialLoginUser = user == null ?
                userRepository.save(new User().socialLoginUserDtoConvertUser(socialLoginUserDto)) :
                user;
        UserLoginDto userLoginInfo = new UserLoginDto();
        userLoginInfo.setEmail(socialLoginUser.getEmail());
        userLoginInfo.setPassword(socialLoginUser.getSocialId());

        return authServiceImpl.getToken(userLoginInfo);

    }

    public User getSocialLoginUser(final  SocialLoginUserDto user) {
        return userRepository
            .findBySocialIdAndLoginType(user.getSocialId(), user.getLoginType())
            .stream()
            .findFirst().orElse(null);
    }
}
