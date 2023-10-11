package org.awesomeboro.awesome_bro.user;

import static org.awesomeboro.awesome_bro.controller.ApiResponse.createSuccess;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.awesomeboro.awesome_bro.controller.ApiResponse;
import org.awesomeboro.awesome_bro.dto.user.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    /**
     * 회원가입
     * @param user
     * @return
     */
    @PostMapping("/signUp")
    public ApiResponse<UserInfoDto> createUser(@Valid @RequestBody UserSignUpRequestDto user) {
        UserInfoDto result = userService.createUser(user);
        return createSuccess(result);
    }

    /**
     * 일반 로그인
     * @return
     */
    @PostMapping("/login")
    public ApiResponse<TokenDto> login(@RequestBody UserLoginDto user) {
        return createSuccess(userService.login(user));
    }

    /**
     * 유저 정보 조회 - id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse<UserInfoDto> getUser(@PathVariable Long id) {
        return createSuccess(userService.findUser(id));
    }

    /**
     * 유저 목록
     */
    @GetMapping("/list")
    public ApiResponse<List<UserInfoDto>> getUserList() {
        return createSuccess(userService.findUserList());
    }

    /**
     * 유저 정보 수정
     * @return
     */

    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<UserInfoDto> updateUser(@RequestBody UserInfoDto user, @PathVariable Long id) {
        return createSuccess(userService.updateUser(user,id));

    }

    /**
     * 소셜 로그인 및 회원가입
     * @param user
     * @return
     */
    @PostMapping("/social")
    public ApiResponse<TokenDto> socialLoginAndSignUp(@RequestBody @Valid UserLoginDto user) {
            return createSuccess(userService.socialLogin(user));
    }

    /**
     * 유저 삭제
     * @param id
     * @return
     */
    @PatchMapping("/delete/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        return createSuccess( userService.deleteUser(id));
    }

}
