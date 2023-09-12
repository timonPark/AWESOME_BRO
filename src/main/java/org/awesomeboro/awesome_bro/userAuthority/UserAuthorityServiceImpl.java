package org.awesomeboro.awesome_bro.userAuthority;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.auth.Authority;
import org.awesomeboro.awesome_bro.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthorityServiceImpl implements UserAuthorityService{
    final private UserAuthorityRepository userAuthorityRepository;

    @Override public UserAuthority createUserAuthority(final User user, final Authority authority) {
        return userAuthorityRepository.save(
                UserAuthority.builder()
                        .user(user)
                        .authority(authority)
                        .build()
        );
    }

    @Override public List<Authority> findByUserIdToAuthority(final Long userId) {
        return
                userAuthorityRepository.findOneWithUsersWithAuthoritiesByUserId(userId)
                        .stream()
                        .map((
                                userAuthority -> userAuthority.getAuthority()
                        )).toList();
    }
}
