package org.awesomeboro.awesome_bro.user;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.awesomeboro.awesome_bro.common.BaseEntity;
import org.awesomeboro.awesome_bro.dto.user.UserDto;
import org.awesomeboro.awesome_bro.userAuthority.UserAuthority;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(name = "User.withUserAuthorities", attributeNodes = {
        @NamedAttributeNode("userAuthorities")
})
@EntityListeners(AuditingEntityListener.class)
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
    @Column(length = 20,nullable = false)
    private String phoneNumber;
    @Column(length = 30,nullable = false)
    private String loginType;
    @Column(length = 200,nullable = true)
    private String socialId;
    @Column(length = 200,nullable = true)
    private String profilePicture;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<UserAuthority> userAuthorities = new ArrayList<>();

    public User socialLoginUserDtoConvertUser(UserDto userDto) {
        this.name = userDto.getName();
        this.email = userDto.getEmail();
        this.nickname = userDto.getNickname();
        this.phoneNumber = "010-0000-0000";
        this.loginType = userDto.getLoginType();
        this.socialId = userDto.getSocialId();
        this.profilePicture = userDto.getProfilePicture();
        return this;
    }

}

