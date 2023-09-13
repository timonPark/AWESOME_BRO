package org.awesomeboro.awesome_bro.user;

import static org.awesomeboro.awesome_bro.controller.ApiResponse.createSuccess;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.controller.ApiResponse;
import org.awesomeboro.awesome_bro.dto.user.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PostMapping
    public ApiResponse<User> createUser(@RequestBody UserDto user) {
        return createSuccess(userService.createUser(user));
    }

    /**
     * 로그인
     * @return
     */
    @PostMapping("/login")
    public ApiResponse<TokenDto> login(@RequestBody UserDto user) {
        return createSuccess(userService.login(user));
    }

    // 사용자 조회
    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ApiResponse<UserInfoDto> getUser(@PathVariable Long id) {
        UserInfoDto user = userService.findUser(id);
        return createSuccess(user);
    }

    @GetMapping()
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@RequestParam String name) {
        System.out.println(name);
        return ResponseEntity.ok(userService.getUserWithAuthorities(name).get());
    }

    @PostMapping("/social")
    public ApiResponse<TokenDto> socialLoginAndSignUp(@RequestBody UserDto user) {
            return createSuccess(userService.socialLogin(user));
    }

}
