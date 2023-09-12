package org.awesomeboro.awesome_bro.user;

import org.awesomeboro.awesome_bro.constant.ErrorCode;
import org.awesomeboro.awesome_bro.dto.user.*;
import org.awesomeboro.awesome_bro.exception.PasswordException;
import org.awesomeboro.awesome_bro.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    @Transactional
    @DisplayName("회원가입 - 이미 존재하는 이메일로 회원가입 - 실패")
    public void createUserDuplicateEmail() {
        // given
        UserDto user = new UserDto();
        user.setName("박종훈");
        user.setNickname("roy");
        user.setPassword("12345678");
        user.setEmail("park@marketboro.com");
        user.setPhoneNumber("010-1234-1234");
        user.setLoginType("normal");
        user.setSocialId("33kfkfk");
        userService.createUser(user); // 첫 번째 회원가입


        // 존재하는 이메일로 회원가입
        UserDto user2 = new UserDto();
        user2.setName("박종훈2");
        user2.setNickname("roy2");
        user2.setPassword("12345678");
        user2.setEmail("park@marketboro.com");
        user2.setPhoneNumber("010-1233-1233");
        user2.setLoginType("normal");
        user2.setSocialId("33kfkfk2");

        // then
        Exception exception = assertThrows(UserNotFoundException.class, () -> userService.createUser(user2));
        assertTrue(exception.getMessage().contains(ErrorCode.EMAIL_ALREADY_EXISTS.getMessage()));
    }

    @Test
    @Transactional
    @DisplayName("회원가입 - 비밀번호 자리수 8자리 이하 유효성 검사 - 실패")
    public void createUserPasswordLengthUnder8(){
        // given
        UserDto user = new UserDto();
        user.setName("이관영");
        user.setNickname("스프링king");
        user.setPassword("하나둘셋");
        user.setEmail("rhksdud23000@marketboro.com");
        user.setPhoneNumber("010-1234-1234");
        user.setLoginType("normal");
        // then
        Exception exception = assertThrows(PasswordException.class, () -> userService.createUser(user));
        assertTrue(exception.getMessage().contains(ErrorCode.PASSWORD_LENGTH_ERROR.getMessage()));
    }

    @Test
    @Transactional
    @DisplayName("회원가입 - 비밀번호 자리수 15자리 이상 유효성 검사 - 실패")
    public void createUserPasswordLengthOver15(){
        // given
        UserDto user = new UserDto();
        user.setName("이관영");
        user.setNickname("스프링king");
        user.setPassword("하나둘셋다섯여섯일곱여덟아홉열열하나열둘열셋");
        user.setEmail("rhksdud23000@marketboro.com");
        user.setPhoneNumber("010-1234-1234");
        user.setLoginType("normal");
        // then
        Exception exception = assertThrows(PasswordException.class, () -> userService.createUser(user));
        assertTrue(exception.getMessage().contains(ErrorCode.PASSWORD_LENGTH_ERROR.getMessage()));
    }

    @Test
    @Transactional
    @DisplayName("회원가입 - 성공")
    public void createUserSuccess(){
        // given
        UserDto user = new UserDto();
        user.setName("이관영");
        user.setNickname("스프링king");
        user.setPassword("마켓보로마켓보로");
        user.setEmail("rhksdud23000@marketboro.com");
        user.setPhoneNumber("010-1234-1234");
        user.setLoginType("normal");
        User createdUser = userService.createUser(user);
        // when
        User foundUser = userService.findUser(createdUser.getId());
        // then
        assertThat(foundUser.getName()).isEqualTo(user.getName());
        assertThat(foundUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(foundUser.getLoginType()).isEqualTo(user.getLoginType());
    }

    @Test
    @Transactional
    public void findUserTest() {
        // given
        User user = new User();
        user.setName("이관영");
        user.setNickname("roy");
        user.setEmail("m1234@marketboro.com");
        user.setPhoneNumber("010-1234-1234");
        user.setLoginType("normal");
        user.setSocialId("33kfkfk2");

        // when
//        Long id = userService.createUser(user).getId();
//        // when
//        User foundUser = userService.findUser(id);
//        // then
//        assertThat(foundUser.getName()).isEqualTo(user.getName());
    }

    @Test
    @Transactional
    @DisplayName("로그인 - 성공")
    void loginSuccess() {
        // given
        UserDto user = new UserDto();
        user.setName("이관영");
        user.setNickname("스프링king");
        user.setPassword("마켓보로마켓보로");
        user.setEmail("rhksdud23000@marketboro.com");
        user.setPhoneNumber("010-1234-1234");
        user.setLoginType("normal");
        User createdUser = userService.createUser(user);
        // when
        // then
        assertThat(createdUser.getName()).isEqualTo(user.getName());
        assertThat(createdUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(createdUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(createdUser.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(createdUser.getLoginType()).isEqualTo(user.getLoginType());

        assertEquals("이관영", createdUser.getName());
        assertEquals("스프링king", createdUser.getNickname());
        assertEquals("rhksdud23000@marketboro.com", createdUser.getEmail());
        assertEquals("010-1234-1234", createdUser.getPhoneNumber());
        assertEquals("normal", createdUser.getLoginType());


    }
    @Test
    @Transactional
    @DisplayName("로그인 - 잘못된 비밀번호 로그인 시도 - 실패")
    void loginFailWrongPassword() {
        // given
        UserDto user = new UserDto();
        user.setName("이관영");
        user.setNickname("스프링king");
        user.setPassword("마켓보로마켓보로");
        user.setEmail("rhksdud23000@marketboro.com");
        user.setPhoneNumber("010-1234-1234");
        user.setLoginType("normal");
        userService.createUser(user);

        // 잘못된 비밀번호로 로그인 시도
        UserDto loginUser = new UserDto();
        loginUser.setEmail("rhksdud23000@marketboro.com");
        loginUser.setPassword("잘못된패스워드");
        // when
        // then
        PasswordException exception = assertThrows(PasswordException.class, () -> userService.login(loginUser));
        Assertions.assertEquals(ErrorCode.WRONG_PASSWORD.getMessage(), exception.getMessage());
    }


    @Test
    @Transactional
    @DisplayName("로그인 - 탈퇴한 회원 유효성 검사 - 실패")
    void loginFailWithdrawUser() {
        // given
        UserDto user = new UserDto();
        user.setName("이관영");
        user.setNickname("스프링king");
        user.setPassword("마켓보로마켓보로");
        user.setEmail("rhksdud23000@marketboro.com");
        user.setPhoneNumber("010-1234-1234");
        user.setLoginType("normal");
        User createdUser = userService.createUser(user);
        // when
        userService.deleteUser(createdUser.getId());

        // when & then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.login(user));
        System.out.println(exception.getMessage()+exception.getErrorCode());
        Assertions.assertEquals(ErrorCode.DELETED_USER.getMessage(), exception.getMessage());
    }

    @Test
    @Transactional
    @DisplayName("로그인 - 존재하지 않는 회원 유효성 검사 - 실패")
    void loginFailUndefinedUser() {
        // given
        UserDto user = new UserDto();
        user.setName("이관영");
        user.setNickname("스프링king");
        user.setPassword("마켓보로마켓보로");
        user.setEmail("rhksdud23000@marketboro.com");
        user.setPhoneNumber("010-1234-1234");
        user.setLoginType("normal");
        userService.createUser(user);

        // 잘못된 비밀번호로 로그인 시도
        UserDto loginUser = new UserDto();
        loginUser.setEmail("market@marketboro.com");
        loginUser.setPassword("마켓보로마켓보로");
        // when
        // then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.login(loginUser));
        Assertions.assertEquals(ErrorCode.UNDEFINED_EMAIL.getMessage(), exception.getMessage());
    }



    @Test
    @Transactional
    @DisplayName("소셜아이디와 로그인타입으로 검색후 존재시 유저객체 반환")
    void getSocialLoginUserTestSuccess(){
        // given
        UserDto socialLoginUser = new UserDto();
        socialLoginUser.setName("손흥민");
        socialLoginUser.setNickname("CaptainSon");
        socialLoginUser.setEmail("SonCaptain7@tottenham.com");
        socialLoginUser.setLoginType("kakao");
        socialLoginUser.setSocialId("5007848651");
        socialLoginUser.setProfilePicture("http://k.kakaocdn.net/dn/A1d2E/btsqZyraOkC/J8jJh8kXdC6NuPGNykDtKk/img_640x640.jpg");

        UserDto user2 = new UserDto();
        user2.setName("손흥민");
        user2.setNickname("CaptainSon");
        user2.setEmail("SonCaptain7@tottenham.com");
        user2.setPhoneNumber("010-7777-7777");
        user2.setLoginType("kakao");
        user2.setSocialId("5007848651");
        user2.setProfilePicture("http://k.kakaocdn.net/dn/A1d2E/btsqZyraOkC/J8jJh8kXdC6NuPGNykDtKk/img_640x640.jpg");
        userService.createUser(user2);

        // when
        User user = userService.getSocialLoginUser(socialLoginUser);

        // then
        assertThat(socialLoginUser.getName()).isEqualTo(user.getName());
        assertThat(socialLoginUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(socialLoginUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(socialLoginUser.getLoginType()).isEqualTo(user.getLoginType());
        assertThat(socialLoginUser.getSocialId()).isEqualTo(user.getSocialId());
        assertThat(socialLoginUser.getProfilePicture()).isEqualTo(user.getProfilePicture());
    }

    @Test
    @DisplayName("소셜아이디와 로그인타입으로 검색후 없을 때 Null 반환")
    void getSocialLoginUserTestFail(){
        // given
        UserDto socialLoginUser = new UserDto();
        socialLoginUser.setName("케인");
        socialLoginUser.setNickname("kain");
        socialLoginUser.setEmail("kain@tottenham.com");
        socialLoginUser.setLoginType("kakao");
        socialLoginUser.setSocialId("5007848644");
        socialLoginUser.setProfilePicture("http://k.kakaocdn.net/dn/A1d2E/btsqZyraOkC/J8jJh8kXdC6NuPGNykDtKk/img_640x640.jpg");

        // when
        User user = userService.getSocialLoginUser(socialLoginUser);

        // then
        assertThat(user).isNull();
    }

    @Test
    @Transactional
    @DisplayName("SocialLoginUserDto로 소셜로그인시 유저객체로 반환 성공")
    void socialLoginTestSuccess(){
        // given
        UserDto socialLoginUser = new UserDto();
        socialLoginUser.setName("케인");
        socialLoginUser.setNickname("kain");
        socialLoginUser.setEmail("kain@tottenham.com");
        socialLoginUser.setLoginType("kakao");
        socialLoginUser.setSocialId("5007848644");
        socialLoginUser.setProfilePicture("http://k.kakaocdn.net/dn/A1d2E/btsqZyraOkC/J8jJh8kXdC6NuPGNykDtKk/img_640x640.jpg");

        // when
        TokenDto user = userService.socialLogin(socialLoginUser);

        // then
//        assertThat(socialLoginUser.getName()).isEqualTo(user.getName());
//        assertThat(socialLoginUser.getNickname()).isEqualTo(user.getNickname());
//        assertThat(socialLoginUser.getEmail()).isEqualTo(user.getEmail());
//        assertThat(socialLoginUser.getLoginType()).isEqualTo(user.getLoginType());
//        assertThat(socialLoginUser.getSocialId()).isEqualTo(user.getSocialId());
//        assertThat(socialLoginUser.getProfilePicture()).isEqualTo(user.getProfilePicture());
    }

    @Test
    @Transactional
    @DisplayName("SocialLoginUserDto로 소셜로그인시 유저객체로 반환 실패 - 유효성검증")
    void socialLoginTestFail(){
        // given
        SocialLoginUserDto socialLoginUser = new SocialLoginUserDto();
        socialLoginUser.setSocialId("5007848644");
        socialLoginUser.setProfilePicture("http://k.kakaocdn.net/dn/A1d2E/btsqZyraOkC/J8jJh8kXdC6NuPGNykDtKk/img_640x640.jpg");


        // then
        assertThrows(DataIntegrityViolationException.class, ()-> {
            // when
//            User user = userService.socialLogin(socialLoginUser);
        });
    }
}
