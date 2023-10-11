package org.awesomeboro.awesome_bro.dto.user;
import lombok.*;
import org.awesomeboro.awesome_bro.user.User;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto {
    private Long id;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String email;
    private String loginType;
    private String socialId;
    private String profilePicture;


    public static UserInfoDto convertToUserInfoDto(User user){
        return UserInfoDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .profilePicture(user.getProfilePicture())
                .loginType(user.getLoginType())
                .build();
    }
}
