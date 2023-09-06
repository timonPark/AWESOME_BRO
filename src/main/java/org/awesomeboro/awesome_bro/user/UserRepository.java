package org.awesomeboro.awesome_bro.user;

import java.util.Optional;

public interface UserRepository {
    Long save(User user);
    User findOne(Long id);
    Optional<User> findByEmail(String email);

}
