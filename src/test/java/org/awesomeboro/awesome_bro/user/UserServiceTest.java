package org.awesomeboro.awesome_bro.user;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    @Transactional
    public void createUserTest() {
        // given
        User user = new User();
        user.setName("이관영");
        user.setNickname("roy");
        user.setPassword("1234");
        user.setSocial_id("33kfkfk");
        user.setPhone_number("010-1234-1234");
        user.setUse_yn("y");
        user.setLogin_type("local");
        // when
        Long id = userService.createUser(user);
        // then
        assertNotEquals(id, null);
    }

    @Test
    public void findUserTest() {
        // given
        Long id = 1L;
        // when
        User foundUser = userService.findUser(id);
        // then
        Assertions.assertThat(foundUser.getName()).isEqualTo("마켓봄프로");
    }
}
