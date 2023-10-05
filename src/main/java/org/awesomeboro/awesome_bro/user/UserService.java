package org.awesomeboro.awesome_bro.user;

import org.awesomeboro.awesome_bro.dto.user.*;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserInfoDto createUser(User user);
    UserInfoDto findUser(Long id);
    TokenDto login(User user);
    Optional<User> getUserWithAuthorities(String email);
    TokenDto socialLogin(final User user);
    UserInfoDto updateUser(UserSignUpDto user, Long id);
    String deleteUser(Long id);
    List<UserInfoDto> findUserList();
}
