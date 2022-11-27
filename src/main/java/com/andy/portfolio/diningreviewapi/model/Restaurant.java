package com.andy.portfolio.diningreviewapi.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="restaurant")
@RequiredArgsConstructor
@Getter @Setter
public class Restaurant {
    @Id
    @GeneratedValue
    Long id;

    @Pattern(regexp = "[a-zA-Z-]+")
    @NonNull
    @Column(name="name")
    private String name;

    @Pattern(regexp = "[a-zA-Z-]+")
    @NonNull
    @Column(name="country")
    private String country;

    @Pattern(regexp = "[\\w -]+")
    @NonNull
    @Column(name="zip_code")
    private String zipCode;

    @Pattern(regexp = "[a-zA-Z-]+")
    @NonNull
    @Column(name="city")
    private String city;

    @Column(name="average_score_peanut")
    private Long averageScorePeanut;

    @Column(name="average_score_egg")
    private Long averageScoreEgg;

    @Column(name="average_score_dairy")
    private Long averageScoreDairy;

    @Column(name="overall_score")
    private Long overallScore;
}
