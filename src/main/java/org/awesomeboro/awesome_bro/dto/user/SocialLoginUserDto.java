package org.awesomeboro.awesome_bro.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SocialLoginUserDto {
	private String name;
	private String email;
	private String nickname;
	private String loginType;
	private String socialId;
	private String profilePicture;

}
