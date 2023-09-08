package org.awesomeboro.awesome_bro.user;

import static org.awesomeboro.awesome_bro.controller.ApiResponse.createSuccess;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.controller.ApiResponse;
import org.awesomeboro.awesome_bro.dto.user.SocialLoginUserDto;
import org.awesomeboro.awesome_bro.exception.UserNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 사용자 생성
    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) {
        System.out.println("user = " + user);
        return createSuccess(userService.signUp(user));
    }

    // 사용자 조회
    @GetMapping("/{id}")
    public ApiResponse<User> getUser(@PathVariable Long id) {
        User user = userService.findUser(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return createSuccess(user);
    }

    @PostMapping("/social")
    public ApiResponse<Long> socialLoginAndSignUp(@RequestBody SocialLoginUserDto user) {
        return createSuccess(userService.socialLogin(user).getId());
    }

}
