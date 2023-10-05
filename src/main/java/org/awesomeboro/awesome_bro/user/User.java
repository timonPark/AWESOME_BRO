package org.awesomeboro.awesome_bro.user;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.awesomeboro.awesome_bro.common.BaseEntity;
import org.awesomeboro.awesome_bro.dto.user.UserSignUpDto;
import org.awesomeboro.awesome_bro.userAuthority.UserAuthority;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedEntityGraph(name = "User.withUserAuthorities", attributeNodes = {
        @NamedAttributeNode("userAuthorities")
})
public class User extends BaseEntity {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50,nullable = false)
    private String name;
    @Column(unique = true,length = 50,nullable = false)
    private String email;
    @Column(length = 50,nullable = false)
    private String nickname;
    @Column(length = 128,nullable = true)
    private String password;
    @Column(length = 20,nullable = true)
    private String phoneNumber;
    @Column(length = 30,nullable = false)
    private String loginType;
    @Column(length = 200,nullable = true)
    private String socialId;
    @Column(length = 200,nullable = true)
    private String profilePicture;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserAuthority> userAuthorities = new ArrayList<>();

    public void getTokenFromSocial(User user) {
        this.email = user.getEmail();
        this.password = user.getSocialId();
    }

    public void updateUserInfo(UserSignUpDto user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phoneNumber = user.getPhoneNumber();
        this.profilePicture = user.getProfilePicture();
    }

}

