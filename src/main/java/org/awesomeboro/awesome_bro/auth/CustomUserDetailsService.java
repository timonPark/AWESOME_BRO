package org.awesomeboro.awesome_bro.auth;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.dto.user.CustomUserDetailDto;
import org.awesomeboro.awesome_bro.exception.UserNotFoundException;
import org.awesomeboro.awesome_bro.user.User;
import org.awesomeboro.awesome_bro.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


import static org.awesomeboro.awesome_bro.constant.ErrorCode.*;


@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String userEmail) {
       User validatedUser = userRepository.findByEmailAndUseYn(userEmail,"y")
               .orElseThrow(() -> new UserNotFoundException(UNDEFINED_EMAIL));
        CustomUserDetailDto customUserDetailDto = new CustomUserDetailDto(validatedUser);
        return new CustomUserDetails(customUserDetailDto);
    }
}
