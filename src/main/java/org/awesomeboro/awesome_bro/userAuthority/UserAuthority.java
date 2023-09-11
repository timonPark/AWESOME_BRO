package org.awesomeboro.awesome_bro.userAuthority;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.awesomeboro.awesome_bro.auth.Authority;
import org.awesomeboro.awesome_bro.common.BaseEntity;
import org.awesomeboro.awesome_bro.user.User;

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