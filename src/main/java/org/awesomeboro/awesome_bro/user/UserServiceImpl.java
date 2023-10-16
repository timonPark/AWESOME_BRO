package org.awesomeboro.awesome_bro.user;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.auth.AuthService;
import org.awesomeboro.awesome_bro.dto.user.*;
import org.awesomeboro.awesome_bro.exception.PasswordException;
import org.awesomeboro.awesome_bro.exception.UserNotFoundException;
import org.awesomeboro.awesome_bro.userAuthority.UserAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.awesomeboro.awesome_bro.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final AuthService authService;
    private final AuthService authorityService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    /**
     * 회원가입
     * @param userInfo
     * @return
     */
    @Override
    @Transactional
    public UserInfoDto createUser(UserSignUpRequestDto userInfo){
        // 1.이메일 및 비밀번호 검증
        signUpValidate(userInfo);
        // 비밀번호 암호화 추가
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        // 2. 유저 정보 만들기
        User saveUser = new User();
        saveUser.updateFromDto(userInfo);
        userRepository.save(saveUser);

        // 3. 유저권한 매핑
        UserAuthority.createUserAuthority(saveUser,authorityService.findById(3L).orElseThrow());
        return UserInfoDto.convertToUserInfoDto(saveUser);
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
        return authService.getToken(user);
    }

    /**
     * 유저 탈퇴
     * @param id
     */
    @Override
    @Transactional
    public String deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow();
        user.softDelete();
        return "ok";
    }

    /**
     * 유저 조회
     * @param id
     * @return
     */
    @Override
    public UserInfoDto findUser(Long id){
        User user = userRepository.findByIdAndUseYn(id,"y").orElseThrow(() -> new UserNotFoundException(UNDEFINED_EMAIL));
        return UserInfoDto.convertToUserInfoDto(user);
    }

    /**
     * 유저 목록
     */
    @Override
    public List<UserInfoDto> findUserList(){
        List<User> userList = userRepository.findAllByUseYn("y");
        return userList.stream().map(UserInfoDto::convertToUserInfoDto).toList();
    }

    /**
     * 소셜 로그인
     * @param user
     * @return
     */
    @Override
    @Transactional
    public TokenDto socialLogin(final UserLoginDto user) {
        User saveUser = new User();
        saveUser.updateFromDto(user);
        User socialLoginUser = checkForSocialLogin(user,saveUser);
        UserLoginDto userLoginInfo = new UserLoginDto();
        userLoginInfo.getTokenFromSocial(socialLoginUser);
        return authService.getToken(userLoginInfo);
    }

    /**
     * 유저 정보 수정
     * @param user
     * @param id
     * @return
     */
    @Override
    @Transactional
    public UserInfoDto updateUser(UserInfoDto user, Long id) {
        User userInfo = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(UNDEFINED_ID));
        userInfo.updateUserInfo(user);
        return UserInfoDto.convertToUserInfoDto(userInfo);


    }

    private void signUpValidate(UserSignUpRequestDto user){
        Optional<User> existUserCheck = userRepository.findByEmail(user.getEmail());
        // 1. 이메일 이미 존재하면 에러 처리
        if(existUserCheck.isPresent()){
            throw new UserNotFoundException(EMAIL_ALREADY_EXISTS);
        }
        // 2. 비밀번호 자리수 8자리 이하 에러 처리
        if(user.getPassword().length() < 8 || user.getPassword().length() > 15){
            throw new PasswordException(PASSWORD_LENGTH_ERROR);
        }
    }

    private User checkForSocialLogin(UserLoginDto user,User saveUser){
        try {
            User socialUser = userRepository
                    .findBySocialIdAndLoginType(user.getSocialId(), user.getLoginType())
                    .stream()
                    .findFirst().orElse(null);
            if (socialUser == null) {
                User socialLoginUser;
                socialLoginUser = userRepository.save(saveUser);
                UserAuthority.createUserAuthority(socialLoginUser,authorityService.findById(3L).orElseThrow());
                return socialLoginUser;
            } else {
                return socialUser;
            }
        }catch (Exception e){
            throw new RuntimeException("소셜 로그인 실패");
        }
    }

    @Transactional
    public String resetPassword(String email, String name){
        User user = userRepository.findByEmailAndName(email, name);
        if(user.getEmail().isEmpty()){
            throw new UserNotFoundException(UNDEFINED_EMAIL);
        }
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);
        emailService.sendTempPassword(email, tempPassword);
        return tempPassword;
    }



}
