package org.awesomeboro.awesome_bro.user;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findBySocialIdAndLoginType(String socialId, String loginType);
}
