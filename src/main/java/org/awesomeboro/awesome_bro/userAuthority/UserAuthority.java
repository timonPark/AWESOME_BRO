package org.awesomeboro.awesome_bro.userAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthority extends BaseEntity {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "authority_id")
    Authority authority;
}