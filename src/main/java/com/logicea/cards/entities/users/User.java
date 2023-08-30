package com.logicea.cards.entities.users;

import com.logicea.cards.helper.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "user_type")
    private long userTypeId;

    @Column(name = "enabled")
    private boolean enabled;

    @JoinColumn(name = "user_type", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private UserType userType;
}