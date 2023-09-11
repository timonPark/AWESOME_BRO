package org.awesomeboro.awesome_bro.auth;

import jakarta.persistence.*;
import lombok.*;
import org.awesomeboro.awesome_bro.common.BaseEntity;
import org.awesomeboro.awesome_bro.userAuthority.UserAuthority;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority extends BaseEntity {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50,nullable = false)
    private String name;

    @Column(length = 50,nullable = false)
    private String label;

    @OneToMany(mappedBy = "authority")
    List<UserAuthority> userAuthorities = new ArrayList<>();
}






