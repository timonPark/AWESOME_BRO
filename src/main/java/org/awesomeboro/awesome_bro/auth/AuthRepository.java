package org.awesomeboro.awesome_bro.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository  extends JpaRepository<Authority, Long> {
     Optional<Authority>  findByAuthorityName(String roles);
}
