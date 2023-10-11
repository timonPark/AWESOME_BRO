package org.awesomeboro.awesome_bro.user;

import org.awesomeboro.awesome_bro.dto.user.*;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserInfoDto createUser(UserSignUpRequestDto user);
    UserInfoDto findUser(Long id);
    TokenDto login(UserLoginDto user);
    Optional<User> getUserWithAuthorities(String email);
    TokenDto socialLogin(final UserLoginDto user);
    UserInfoDto updateUser(UserInfoDto user, Long id);
    String deleteUser(Long id);
    List<UserInfoDto> findUserList();
}
