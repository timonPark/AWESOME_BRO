package org.awesomeboro.awesome_bro.user;

import org.awesomeboro.awesome_bro.dto.user.SocialLoginUserDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
    Long createUser(User user);
    User findUser(Long id);
    public User socialLogin(@RequestBody SocialLoginUserDto user);
}
