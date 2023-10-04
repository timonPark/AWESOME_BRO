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
     * @return
     */

    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@RequestBody UserSignUpDto user, @PathVariable Long id) {
        return userService.updateUser(user,id);

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
