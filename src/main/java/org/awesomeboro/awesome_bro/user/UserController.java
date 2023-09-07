package org.awesomeboro.awesome_bro.user;

import static org.awesomeboro.awesome_bro.controller.ApiResponse.createSuccess;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.controller.ApiResponse;
import org.awesomeboro.awesome_bro.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    // 사용자 생성
    @PostMapping
    public ApiResponse<Long> createUser(@RequestBody User user) {
        String userPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(userPassword);
        Long userId = userService.createUser(user);
        return createSuccess(userId);
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
