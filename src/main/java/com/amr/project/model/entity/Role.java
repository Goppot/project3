package com.amr.project.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include()
    @Column(name = "role_name", unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

}
