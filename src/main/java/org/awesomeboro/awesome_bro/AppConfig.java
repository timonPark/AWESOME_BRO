package org.awesomeboro.awesome_bro;


import org.awesomeboro.awesome_bro.user.UserRepository;
import org.awesomeboro.awesome_bro.user.UserRepositoryImpl;
import org.awesomeboro.awesome_bro.user.UserService;
import org.awesomeboro.awesome_bro.user.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public UserService userService(){
        return new UserServiceImpl(userRepository());
    }
    @Bean
    public UserRepository userRepository(){
        return new UserRepositoryImpl();
    }
}
