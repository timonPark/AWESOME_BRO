package org.awesomeboro.awesome_bro.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfoDto {
    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String loginType;
    private String phoneNumber;
    private String socialId;
    private String profilePicture;


}
