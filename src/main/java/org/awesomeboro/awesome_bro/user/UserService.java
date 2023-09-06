package org.awesomeboro.awesome_bro.user;



public interface UserService {
    Long createUser(User user);
    User findUser(Long id);
}
