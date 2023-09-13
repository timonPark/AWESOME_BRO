package org.awesomeboro.awesome_bro.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findBySocialIdAndLoginType(String socialId, String loginType);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);

}
