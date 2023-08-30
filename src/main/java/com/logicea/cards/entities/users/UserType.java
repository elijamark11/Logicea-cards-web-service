package com.logicea.cards.entities.users;

import com.logicea.cards.helper.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user_types")
public class UserType extends BaseEntity {
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 250)
    @Column(name = "description", length = 250)
    private String description;
}