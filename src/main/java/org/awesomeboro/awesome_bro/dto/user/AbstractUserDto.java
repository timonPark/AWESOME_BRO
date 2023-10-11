package org.awesomeboro.awesome_bro.dto.user;

import lombok.Data;

@Data
public abstract class AbstractUserDto {
    protected String name;
    protected String email;
    protected String password;
    protected String nickname;
    protected String loginType;
    protected String phoneNumber;
    protected String socialId;
    protected String profilePicture;
}
