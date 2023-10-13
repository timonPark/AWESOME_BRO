package org.awesomeboro.awesome_bro.dto.user;

import lombok.Data;
import org.awesomeboro.awesome_bro.user.User;
import org.awesomeboro.awesome_bro.userAuthority.UserAuthority;

import java.util.List;


@Data
public class CustomUserDetailDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private List<UserAuthority> userRoles;

    public CustomUserDetailDto(User validatedUser) {
        this.id = validatedUser.getId();
        this.password = validatedUser.getPassword();
        this.email = validatedUser.getEmail();
        this.name = validatedUser.getName();
        this.nickname = validatedUser.getNickname();
        this.phoneNumber = validatedUser.getPhoneNumber();
        this.userRoles = validatedUser.getUserAuthorities();
    }
}
