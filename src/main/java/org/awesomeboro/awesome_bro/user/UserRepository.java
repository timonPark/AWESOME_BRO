package org.awesomeboro.awesome_bro.user;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findBySocialIdAndLoginType(String socialId, String loginType);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndUseYn(String email, String useYn);

    Optional<User> findByIdAndUseYn(Long id, String useYn);

    List<User> findAllByUseYn(String useYn);
}
