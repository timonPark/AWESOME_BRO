package org.awesomeboro.awesome_bro.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UserDto {

    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @Size(min = 1, max = 50)
    private String email;

    @Nullable
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 128)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 8, max = 128)
    private String nickname;

    @NotNull
    @Size(min = 2, max = 30)
    private String loginType;

    @Nullable
    @Size(min = 1, max = 20)
    private String phoneNumber;

    @Nullable
    @Size(min = 2, max = 200)
    private String socialId;

    @Nullable
    @Size(min = 2, max = 50)
    private String profilePicture;

}
