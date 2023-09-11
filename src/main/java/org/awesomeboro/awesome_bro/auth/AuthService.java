package org.awesomeboro.awesome_bro.auth;

import org.awesomeboro.awesome_bro.dto.user.TokenDto;
import org.awesomeboro.awesome_bro.dto.user.UserDto;
import org.awesomeboro.awesome_bro.dto.user.UserLoginDto;

import java.util.Optional;

public interface AuthService {
    TokenDto getToken(UserDto user);
    Optional<Authority> findByAuthorityName(String authorityName);

    Authority save(Authority authority);

    Optional<Authority> findById(long l);
}
