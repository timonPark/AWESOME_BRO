package org.awesomeboro.awesome_bro.userAuthority;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.awesomeboro.awesome_bro.auth.Authority;
import org.awesomeboro.awesome_bro.common.BaseEntity;
import org.awesomeboro.awesome_bro.user.User;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserAuthority extends BaseEntity {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "authority_id")
    private Authority authority;

    //== 권한생성 메서드==//
    private void setAuthority(Authority authority) {
        this.authority = authority;
        authority.getUserAuthorities().add(this);
    }
    private void setUser(User user) {
        this.user = user;
        user.getUserAuthorities().add(this);
    }

    /**
     * 권한 생성 메서드
     *
     * @param user
     * @param authority
     */
    public static void createUserAuthority(User user, Authority authority) {
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setUser(user);
        userAuthority.setAuthority(authority);
    }
}