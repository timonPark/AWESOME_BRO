package org.awesomeboro.awesome_bro.user;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class User {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,length = 50,nullable = false)
    private String email;
    @Column(length = 50,nullable = false)
    private String name;
    @Column(length = 50,nullable = false)
    private String nickname;
    @Column(length = 128,nullable = true)
    private String password;
    @Column(length = 20,nullable = false)
    private String phone_number;
    @Column(length = 1,nullable = false)
    private String use_yn;
    @Column(length = 30,nullable = false)
    private String login_type;
    @Column(length = 200,nullable = false)
    private String social_id;
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updated_at;

}
