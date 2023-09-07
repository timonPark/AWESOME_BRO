package org.awesomeboro.awesome_bro.user;

import lombok.RequiredArgsConstructor;
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
}
