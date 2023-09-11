package org.awesomeboro.awesome_bro.userAuthority;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
    @EntityGraph(attributePaths = {"user", "authority"})
    List<UserAuthority> findOneWithUsersWithAuthoritiesByUserId(Long userId);

}
