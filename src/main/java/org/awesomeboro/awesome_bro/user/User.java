package org.awesomeboro.awesome_bro.user;


import jakarta.persistence.*;
import java.sql.Timestamp;

import java.util.Set;

import lombok.*;
import org.awesomeboro.awesome_bro.auth.Authority;
import org.awesomeboro.awesome_bro.dto.user.SocialLoginUserDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
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
    @Column(length = 1,nullable = false)
    private String useYn;
    @CreatedDate
    @Column(nullable = false)
    private Timestamp createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Timestamp updatedAt;

    public User socialLoginUserDtoConvertUser(SocialLoginUserDto socialLoginUserDto) {
        this.name = socialLoginUserDto.getName();
        this.email = socialLoginUserDto.getEmail();
        this.nickname = socialLoginUserDto.getNickname();
        this.phoneNumber = "010-0000-0000";
        this.loginType = socialLoginUserDto.getLoginType();
        this.socialId = socialLoginUserDto.getSocialId();
        this.profilePicture = socialLoginUserDto.getProfilePicture();
        this.useYn = "y";
        return this;
    }
  
    
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
