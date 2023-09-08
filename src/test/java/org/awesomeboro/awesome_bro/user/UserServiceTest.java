package org.awesomeboro.awesome_bro.user;

import org.awesomeboro.awesome_bro.dto.user.SocialLoginUserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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
    public void createUserTest() {
        // given
        User user = new User();
        user.setName("박종훈");
        user.setNickname("roy");
        user.setPassword("1234");
        user.setEmail("park@marketboro.com");
        user.setPhoneNumber("010-1234-1234");
        user.setLoginType("regular");
        user.setSocialId("33kfkfk");
        user.setUseYn("y");

        // when
        User user1= userService.signUp(user);
        // then
        assertEquals(user1.getName(), "박종훈");
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
        user.setLoginType("regular2");
        user.setSocialId("33kfkfk2");
        user.setUseYn("y");

        // when
        Long id = userService.createUser(user);
        // when
        User foundUser = userService.findUser(id);
        // then
        assertThat(foundUser.getName()).isEqualTo(user.getName());
    }

    @Test
    @Transactional
    @DisplayName("소셜아이디와 로그인타입으로 검색후 존재시 유저객체 반환")
    void getSocialLoginUserTestSuccess(){
        // given
        SocialLoginUserDto socialLoginUser = new SocialLoginUserDto();
        socialLoginUser.setName("손흥민");
        socialLoginUser.setNickname("CaptainSon");
        socialLoginUser.setEmail("SonCaptain7@tottenham.com");
        socialLoginUser.setLoginType("kakao");
        socialLoginUser.setSocialId("5007848651");
        socialLoginUser.setProfilePicture("http://k.kakaocdn.net/dn/A1d2E/btsqZyraOkC/J8jJh8kXdC6NuPGNykDtKk/img_640x640.jpg");

        User user2 = new User();
        user2.setName("손흥민");
        user2.setNickname("CaptainSon");
        user2.setEmail("SonCaptain7@tottenham.com");
        user2.setPhoneNumber("010-7777-7777");
        user2.setLoginType("kakao");
        user2.setSocialId("5007848651");
        user2.setProfilePicture("http://k.kakaocdn.net/dn/A1d2E/btsqZyraOkC/J8jJh8kXdC6NuPGNykDtKk/img_640x640.jpg");
        user2.setUseYn("y");
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
        SocialLoginUserDto socialLoginUser = new SocialLoginUserDto();
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
        SocialLoginUserDto socialLoginUser = new SocialLoginUserDto();
        socialLoginUser.setName("케인");
        socialLoginUser.setNickname("kain");
        socialLoginUser.setEmail("kain@tottenham.com");
        socialLoginUser.setLoginType("kakao");
        socialLoginUser.setSocialId("5007848644");
        socialLoginUser.setProfilePicture("http://k.kakaocdn.net/dn/A1d2E/btsqZyraOkC/J8jJh8kXdC6NuPGNykDtKk/img_640x640.jpg");

        // when
        User user = userService.socialLogin(socialLoginUser);

        // then
        assertThat(socialLoginUser.getName()).isEqualTo(user.getName());
        assertThat(socialLoginUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(socialLoginUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(socialLoginUser.getLoginType()).isEqualTo(user.getLoginType());
        assertThat(socialLoginUser.getSocialId()).isEqualTo(user.getSocialId());
        assertThat(socialLoginUser.getProfilePicture()).isEqualTo(user.getProfilePicture());
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
            User user = userService.socialLogin(socialLoginUser);
        });
    }
}
