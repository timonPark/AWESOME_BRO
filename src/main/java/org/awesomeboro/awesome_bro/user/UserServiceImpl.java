package org.awesomeboro.awesome_bro.user;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.auth.AuthService;
import org.awesomeboro.awesome_bro.auth.Authority;
import org.awesomeboro.awesome_bro.dto.user.*;
import org.awesomeboro.awesome_bro.exception.UserNotFoundException;
import org.awesomeboro.awesome_bro.utils.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final UserCommonService userCommonService;

    /**
     * 회원가입
     * @param user
     * @return
     */
    @Override
    @Transactional
    public User createUser(UserDto user){
        // 이메일 및 비밀번호 검증
        signUpValidate(user);
        // 가입 안되어 있으면 진행
        // 3. 유저 정보 만들기
        User userInfo = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(passwordEncoder.encode(user.getPassword()))
                .phoneNumber(user.getPhoneNumber())
                .loginType(user.getLoginType())
                .socialId(user.getSocialId())
                .profilePicture(user.getProfilePicture())
                .build();

        return userCommonService.createUserAuthorityToMapping(userRepository.save(userInfo)).getUser();
    }

    /**
     * 로그인
     * @param user
     * @return
     */
    public TokenDto login(UserDto user){
        // 1. 이메일, 비밀번호가 없으면 에러 처리
        if(user.getEmail().length() < 1 || user.getPassword().length() < 1){
            throw new RuntimeException("이메일과 비밀번호를 입력해주세요.");
        }
        // 2. 가입되지 않은 이메일이면 에러 처리
//        if(userRepository.findByEmail(user.getEmail()).isEmpty()){
//            throw new RuntimeException("가입되지 않은 이메일입니다.");
//        }
        // 3. 탈퇴한 회원이면 에러 처리
//        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
//        if(userRepository.findByEmail(user.getEmail()).get().getUseYn().equals("n")){
//            throw new UserNotFoundException(user.getEmail());
//        }
        return authService.getToken(user);
    }



    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String email) {
        return userRepository.findOneWithUserAuthoritiesByEmail(email);
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithUserAuthoritiesByEmail);
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
    public TokenDto socialLogin(final UserDto user) {
        return socialLoginProgress(getSocialLoginUser(user), user);
    }

    private TokenDto socialLoginProgress(final User user, UserDto userDto) {
        User socialLoginUser = user == null ?
                userCommonService.createUserAuthorityToMapping(
                        userRepository.save(
                                new User().socialLoginUserDtoConvertUser(userDto)
                        )
                )
                        .getUser():
                user;

        UserDto userLoginInfo = new UserDto();
        userLoginInfo.setEmail(socialLoginUser.getEmail());
        userLoginInfo.setPassword(socialLoginUser.getSocialId());

        return authService.getToken(userLoginInfo);

    }

    public User getSocialLoginUser(final  UserDto user) {
        return userRepository
            .findBySocialIdAndLoginType(user.getSocialId(), user.getLoginType())
            .stream()
            .findFirst().orElse(null);
    }

    public void signUpValidate(UserDto user){
        // 1. 이메일 이미 존재하면 에러 처리
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        // 2. 비밀번호 자리수 8자리 이하 에러 처리
        if(user.getPassword().length() < 8){
            throw new RuntimeException("비밀번호는 8자리 이상이어야 합니다.");
        }
    }
}
