package org.awesomeboro.awesome_bro.user;

import org.awesomeboro.awesome_bro.dto.user.*;

import java.util.Optional;

public interface UserService {
    UserInfoDto createUser(UserDto user);
    UserInfoDto findUser(Long id);
    TokenDto login(UserDto user);
    Optional<User> getMyUserWithAuthorities();

    Optional<User> getUserWithAuthorities(String email);
//    public User socialLogin(@RequestBody SocialLoginUserDto user);
    TokenDto socialLogin(final UserDto user);
}
