package org.awesomeboro.awesome_bro.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSignUpRequestDto extends AbstractUserDto{
    @NotBlank(message = "이름은 필수 입니다.")
    @Size(min = 2, max = 5,message = "이름은 2자 이상 5자 이하입니다.")
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 128, message = "비밀번호는 8자 이상 128자 이하입니다.")
    private String password;

    @Size(min = 2, max = 50)
    private String nickname;

    @Size(min = 1, max = 20,message = "전화번호는 1자 이상 20자 이하입니다.")
    private String phoneNumber;

    @NotBlank(message = "이메일은 필수 입니다.")
    @Size(min = 1, max = 50)
    private String email;

    @NotBlank(message = "로그인타입은 필수 입니다.")
    @Size(min = 2, max = 30)
    private String loginType;

    @Size(min = 2, max = 200)
    private String socialId;

    @Size(min = 2, max = 50)
    private String profilePicture;

}