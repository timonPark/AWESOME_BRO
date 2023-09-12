package org.awesomeboro.awesome_bro.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.auth.AuthService;
import org.awesomeboro.awesome_bro.userAuthority.UserAuthority;
import org.awesomeboro.awesome_bro.userAuthority.UserAuthorityService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommonService {
    final private AuthService authorityService;
    final private UserAuthorityService userAuthorityService;

    @Transactional
    public UserAuthority createUserAuthorityToMapping(User user){
        return userAuthorityService.createUserAuthority(user, authorityService.findById(3L).orElseThrow());
    }

}
