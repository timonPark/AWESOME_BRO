package org.awesomeboro.awesome_bro.dto.user;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserSignUpResponseDto {
    private Long id;

    private String name;

    private String nickname;

    private String phoneNumber;

    private String email;

    private String loginType;

    private String socialId;

    private String profilePicture;

//    @Builder
//    public UserSignUpResponseDto(Long id, String name, String nickname, String phoneNumber, String email, String loginType, String socialId, String profilePicture) {
//        this.id = id;
//        this.name = name;
//        this.nickname = nickname;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.loginType = loginType;
//        this.socialId = socialId;
//        this.profilePicture = profilePicture;
//    }



}
