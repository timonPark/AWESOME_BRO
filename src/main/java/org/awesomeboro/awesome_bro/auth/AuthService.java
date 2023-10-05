package org.awesomeboro.awesome_bro.auth;

import org.awesomeboro.awesome_bro.dto.user.TokenDto;
import org.awesomeboro.awesome_bro.user.User;

import java.util.Optional;

public interface AuthService {
    TokenDto getToken(User user);
    Optional<Authority> findById(long l);
}
