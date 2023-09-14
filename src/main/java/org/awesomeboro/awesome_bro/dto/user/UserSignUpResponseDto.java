package org.awesomeboro.awesome_bro.dto.user;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
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
}
