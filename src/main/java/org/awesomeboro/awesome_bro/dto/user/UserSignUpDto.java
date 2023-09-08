package org.awesomeboro.awesome_bro.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDto {

    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 8, max = 128)
    private String password;

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