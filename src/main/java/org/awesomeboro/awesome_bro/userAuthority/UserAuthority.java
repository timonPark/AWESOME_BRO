package org.awesomeboro.awesome_bro.userAuthority;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.awesomeboro.awesome_bro.auth.Authority;
import org.awesomeboro.awesome_bro.common.BaseEntity;
import org.awesomeboro.awesome_bro.user.User;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString(exclude = {"user", "authority"})
public class UserAuthority extends BaseEntity {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
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
    // 기본 생성자는 protected로 선언하여 외부에서의 직접적인 인스턴스 생성을 방지
    protected UserAuthority() {}
}