package org.awesomeboro.awesome_bro.dto.user;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.awesomeboro.awesome_bro.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginDto extends AbstractUserDto{
    @NotBlank(message = "이메일은 필수 입니다.")
    @Size(min = 1, max = 50)
    private String email;
    public void getTokenFromSocial(User user){
        this.email = user.getEmail();
        this.password = user.getSocialId();
    }

}
