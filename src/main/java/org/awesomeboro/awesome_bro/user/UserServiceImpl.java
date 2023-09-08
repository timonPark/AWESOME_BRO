package org.awesomeboro.awesome_bro.user;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.dto.user.SocialLoginUserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;


    @Override
    @Transactional
    public Long createUser(User user){
        return userRepository.save(user).getId();
    }

    @Override
    public User findUser(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public User socialLogin(final SocialLoginUserDto user) {
        return socialLoginProgress(getSocialLoginUser(user), user);
    }

    private User socialLoginProgress(final User user, SocialLoginUserDto socialLoginUserDto) {
        return user == null ?
            userRepository.save(new User().socialLoginUserDtoConvertUser(socialLoginUserDto)) :
            user;
    }

    public User getSocialLoginUser(final  SocialLoginUserDto user) {
        return userRepository
            .findBySocialIdAndLoginType(user.getSocialId(), user.getLoginType())
            .stream()
            .findFirst().orElse(null);
    }
}
