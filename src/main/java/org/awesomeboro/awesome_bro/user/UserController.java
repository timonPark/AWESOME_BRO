package org.awesomeboro.awesome_bro.user;

import static org.awesomeboro.awesome_bro.controller.ApiResponse.createSuccess;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.controller.ApiResponse;
import org.awesomeboro.awesome_bro.dto.user.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 회원가입
     * @param user
     * @return
     */
    @PostMapping("/signUp")
    public ApiResponse<UserInfoDto> createUser(@RequestBody UserDto user) {
        UserInfoDto result = userService.createUser(user);
        return createSuccess(result);
    }

    /**
     * 로그인
     * @return
     */
    @PostMapping("/login")
    public ApiResponse<TokenDto> login(@RequestBody UserDto user) {
        return createSuccess(userService.login(user));
    }

    /**
     * 유저 정보 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse<UserInfoDto> getUser(@PathVariable Long id) {
        UserInfoDto user = userService.findUser(id);
        return createSuccess(user);
    }

    /**
     * 유저 정보 조회 - email
     * @param email
     * @return
     */
    @GetMapping("/email/{email}")
    public ApiResponse<UserInfoDto> getUser(@PathVariable String email) {
        UserInfoDto user = userService.findUser(email);
        return createSuccess(user);
    }

    /**
     *
     * @return
     */

    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<UserSignUpResponseDto> updateUser(@RequestBody UserSignUpDto user, @PathVariable String id) {
        // 하드코딩된 더미 응답 생성
        UserSignUpResponseDto dummyResponse = UserSignUpResponseDto.builder()
                .id(Long.parseLong(id))
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture())
                .loginType(user.getLoginType())
                .socialId(user.getSocialId())
                .build();
        return createSuccess(dummyResponse);
    }


    /**
     * 유저 정보 조회 - email
     * @param email
     * @return
     */
    @GetMapping("/email/{email}")
    public ApiResponse<UserInfoDto> getUser(@PathVariable String email) {
        UserInfoDto user = userService.findUser(email);
        return createSuccess(user);
    }

    /**
     * 소셜 로그인 및 회원가입
     * @param user
     * @return
     */
    @PostMapping("/social")
    public ApiResponse<TokenDto> socialLoginAndSignUp(@RequestBody UserDto user) {
            return createSuccess(userService.socialLogin(user));
    }

}
