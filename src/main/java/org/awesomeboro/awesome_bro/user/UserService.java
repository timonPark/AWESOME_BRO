package org.awesomeboro.awesome_bro.user;

import org.awesomeboro.awesome_bro.dto.user.*;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserInfoDto createUser(UserSignUpRequestDto user);
    UserInfoDto findUser(Long id);
    TokenDto login(UserLoginDto user);
    TokenDto socialLogin(final UserLoginDto user);
    UserInfoDto updateUser(UserUpdateRequestDto user, Long id);
    String deleteUser(Long id);
    List<UserInfoDto> findUserList();
    String resetPassword(String email, String name);
}
