package org.awesomeboro.awesome_bro.auth;

import org.awesomeboro.awesome_bro.dto.user.TokenDto;
import org.awesomeboro.awesome_bro.dto.user.UserLoginDto;

public interface AuthService {
    TokenDto getToken(UserLoginDto user);
}
