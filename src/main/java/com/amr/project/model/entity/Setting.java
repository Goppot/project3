package com.amr.project.model.entity;

import com.amr.project.model.enums.PaymentCategory;

import lombok.*;

import javax.persistence.*;

/**
 * User: Hajimurad Suleymanov
 * Date: 10.11.2021
 * Настройки для платежной системы
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Setting {

    @Id
    @Column(name = "`key`", nullable = false, length = 128)
    @EqualsAndHashCode.Include()
    private String key;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentCategory category;

    @Column(nullable = false, length = 1024)
    private String value;

    public Setting(String key) {
        this.key = key;
    }
}
