package org.awesomeboro.awesome_bro.user;

import org.awesomeboro.awesome_bro.dto.user.SocialLoginUserDto;
import org.awesomeboro.awesome_bro.dto.user.TokenDto;
import org.awesomeboro.awesome_bro.dto.user.UserLoginDto;
import org.awesomeboro.awesome_bro.dto.user.UserSignUpDto;

import java.util.Optional;

public interface UserService {
    User createUser(UserSignUpDto user);
    User findUser(Long id);
    TokenDto login(UserLoginDto user);
    Optional<User> getMyUserWithAuthorities();

    Optional<User> getUserWithAuthorities(String email);
//    public User socialLogin(@RequestBody SocialLoginUserDto user);
    TokenDto socialLogin(final SocialLoginUserDto user);
}
