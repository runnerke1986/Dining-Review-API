package com.andy.portfolio.diningreviewapi.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="USERS") // Attention! Needed to use 'users' as table name since the former used 'user' is a reserved keyword in sql
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NonNull
    private String name;

    private String city;

    private String state;

    @Column(name="ZIPCODE")
    private String zipCode;

    @Column(name="HASPEANUTALLERGY")
    private Boolean hasPeanutAllergy;

    @Column(name="HASEGGALLERGY")
    private Boolean hasEggAllergy;

    @Column(name="HASDAIRYALLERGY")
    private Boolean hasDairyAllergy;

    @Setter(AccessLevel.NONE)
    @Column(name="ISADMIN")
    private Boolean isAdmin;
}
