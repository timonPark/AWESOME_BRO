package org.awesomeboro.awesome_bro.userAuthority;

import org.awesomeboro.awesome_bro.auth.Authority;
import org.awesomeboro.awesome_bro.user.User;

import java.util.List;

public interface UserAuthorityService {
    UserAuthority createUserAuthority(User user, Authority authority);
    List<Authority> findByUserIdToAuthority(Long userId);
}
