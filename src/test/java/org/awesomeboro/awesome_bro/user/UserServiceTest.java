package org.awesomeboro.awesome_bro.user;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void createUserTest() {
        // given
        User user = new User();
        user.setName("박종훈");
        user.setNickname("roy");
        user.setPassword("1234");
        user.setEmail("park@marketboro.com");
        user.setPhoneNumber("010-1234-1234");
        user.setLoginType("regular");
        user.setSocialId("33kfkfk");
        user.setUseYn("y");

        // when
        User user1= userService.signUp(user);
        // then
        assertEquals(user1.getName(), "박종훈");
    }

    @Test
    public void findUserTest() {
        // given
        Long id = 1L;
        // when
        User foundUser = userService.findUser(id);
        // then
        Assertions.assertThat(foundUser.getName()).isEqualTo("이관영");
    }
}
