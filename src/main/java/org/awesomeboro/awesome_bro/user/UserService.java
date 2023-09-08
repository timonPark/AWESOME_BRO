package org.awesomeboro.awesome_bro.user;



public interface UserService {
    User signUp(User user);
    User findUser(Long id);
}
