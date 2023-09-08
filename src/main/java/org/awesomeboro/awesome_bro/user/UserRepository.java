package org.awesomeboro.awesome_bro.user;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findBySocialIdAndLoginType(String socialId, String loginType);
    Optional<User> findByEmail(String username);

    @EntityGraph(attributePaths = "authorities") // EAGER 조회로 authorities 정보까지 조회
    Optional<User> findOneWithAuthoritiesByEmail(String email);
}
