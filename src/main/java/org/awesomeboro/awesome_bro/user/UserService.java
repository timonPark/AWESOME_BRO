package org.awesomeboro.awesome_bro.user;


import org.awesomeboro.awesome_bro.auth.Token;

import java.util.Optional;

public interface UserService {
    User signUp(User user);
    User findUser(Long id);
    Token login(User user);



    Optional<User> getMyUserWithAuthorities();

    Optional<User> getUserWithAuthorities(String email);
}
