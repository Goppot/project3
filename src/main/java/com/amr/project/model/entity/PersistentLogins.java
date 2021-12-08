package com.amr.project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "persistent_logins")
@Data
@AllArgsConstructor
public class PersistentLogins {
    @Column(name = "username", nullable = false)
    private String username;

    @Id
    @Column(name = "series", nullable = false)
    private String series;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "last_used", nullable = false)
    private LocalDateTime lastUsed;

    public PersistentLogins() {

    }
}
