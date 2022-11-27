package com.andy.portfolio.diningreviewapi.model;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name="users") // Attention! Used 'users' as table name since the former used 'user' is a reserved keyword in sql
@Getter @Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;


    @Column(name="name",unique = true)
    @NonNull
    @Pattern(regexp = "[a-zA-Z-]+")
    @NotBlank
    private String name;

    @NonNull
    @Pattern(regexp = "[a-zA-Z-]+")
    @Column(name="city")
    private String city;

    @NonNull
    @Pattern(regexp = "[a-zA-Z-]+")
    @Column(name="state")
    private String state;

    @NonNull
    @Pattern(regexp = "[\\w -]+")
    @Column(name="zipcode")
    private String zipCode;

    @Column(name="has_peanut_allergy")
    private Boolean hasPeanutAllergy;

    @Column(name="has_egg_allergy")
    private Boolean hasEggAllergy;

    @Column(name="has_dairy_allergy")
    private Boolean hasDairyAllergy;

    @Setter(AccessLevel.NONE)
    @Column(name="is_admin")
    private Boolean isAdmin;
}
