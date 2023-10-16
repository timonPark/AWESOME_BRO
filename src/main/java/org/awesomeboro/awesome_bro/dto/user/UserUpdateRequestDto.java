package org.awesomeboro.awesome_bro.dto.user;

import lombok.Data;

@Data
public class UserUpdateRequestDto {
    private Long id;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String email;
    private String loginType;
    private String socialId;
    private String profilePicture;
}
