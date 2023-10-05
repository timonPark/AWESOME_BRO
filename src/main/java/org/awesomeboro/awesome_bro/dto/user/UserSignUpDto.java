package org.awesomeboro.awesome_bro.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserSignUpDto {

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    @Size(min = 2, max = 50)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 128)
    private String password;

    @Size(min = 2, max = 50)
    private String nickname;

    @Size(min = 1, max = 20)
    private String phoneNumber;

    @NotEmpty(message = "이메일은 필수 입니다.")
    @Size(min = 1, max = 50)
    private String email;

    @NotEmpty(message = "로그인타입은 필수 입니다.")
    @Size(min = 2, max = 30)
    private String loginType;

    @Size(min = 2, max = 200)
    private String socialId;

    @Size(min = 2, max = 50)
    private String profilePicture;

}