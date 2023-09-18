package org.awesomeboro.awesome_bro.dto.user;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserSignUpResponseDto {
    @NotNull
    @Size(min = 2, max = 50)
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @Size(min = 2, max = 50)
    private String nickname;

    @NotNull
    @Size(min = 1, max = 20)
    private String phoneNumber;

    @NotNull
    @Size(min = 1, max = 50)
    private String email;

    @NotNull
    @Size(min = 2, max = 30)
    private String loginType;

    @Nullable
    @Size(min = 2, max = 200)
    private String socialId;

    @Nullable
    @Size(min = 2, max = 50)
    private String profilePicture;

    @Builder
    public UserSignUpResponseDto(Long id, String name, String nickname, String phoneNumber, String email, String loginType, String socialId, String profilePicture) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.loginType = loginType;
        this.socialId = socialId;
        this.profilePicture = profilePicture;
    }



}
