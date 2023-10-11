package org.awesomeboro.awesome_bro.auth;

import org.awesomeboro.awesome_bro.dto.user.TokenDto;
import org.awesomeboro.awesome_bro.dto.user.UserLoginDto;

import java.util.Optional;

public interface AuthService {
    TokenDto getToken(UserLoginDto user);
    Optional<Authority> findById(long l);
}
