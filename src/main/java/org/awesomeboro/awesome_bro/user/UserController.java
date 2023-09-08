package org.awesomeboro.awesome_bro.user;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    // 사용자 생성
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("user = " + user);
//        Long userId = userService.createUser(user);
        return ResponseEntity.ok(userService.signUp(user));
    }

    // 사용자 조회
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findUser(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return ResponseEntity.ok(user);
    }

}
