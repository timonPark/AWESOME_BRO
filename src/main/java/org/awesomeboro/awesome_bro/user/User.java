package org.awesomeboro.awesome_bro.user;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.awesomeboro.awesome_bro.common.BaseEntity;
import org.awesomeboro.awesome_bro.dto.user.AbstractUserDto;
import org.awesomeboro.awesome_bro.dto.user.UserInfoDto;
import org.awesomeboro.awesome_bro.userAuthority.UserAuthority;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString
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
    @ToString.Exclude
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 200)
    private String profilePicture;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAuthority> userAuthorities = new ArrayList<>();
    public void updateUserInfo(UserInfoDto user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phoneNumber = user.getPhoneNumber();
        this.profilePicture = user.getProfilePicture();
    }

    public void updateFromDto(AbstractUserDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.nickname = dto.getNickname();
        this.password = dto.getPassword();
        this.phoneNumber = dto.getPhoneNumber();
        this.loginType = dto.getLoginType();
        this.socialId = dto.getSocialId();
        this.profilePicture = dto.getProfilePicture();
    }
}

