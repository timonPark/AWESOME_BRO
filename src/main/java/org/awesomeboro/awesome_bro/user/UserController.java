package org.awesomeboro.awesome_bro.user;

import static org.awesomeboro.awesome_bro.controller.ApiResponse.createSuccess;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.auth.Token;
import org.awesomeboro.awesome_bro.controller.ApiResponse;
import org.awesomeboro.awesome_bro.exception.UserNotFoundException;

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
    public ApiResponse<User> createUser(@RequestBody User user) {
        return createSuccess(userService.signUp(user));
    }

    /**
     * 로그인
     * @return
     */
    @PostMapping("/login")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ApiResponse<Token> login(@RequestBody User user) {
        return createSuccess(userService.login(user));
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

}
