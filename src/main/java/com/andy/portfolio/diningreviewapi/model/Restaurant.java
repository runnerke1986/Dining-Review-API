package com.andy.portfolio.diningreviewapi.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="restaurant")
@RequiredArgsConstructor
//@NoArgsConstructor
@Getter @Setter
public class Restaurant {
    @Id
    @GeneratedValue
    Long id;

    @Pattern(regexp = "[a-zA-Z- ]+")
    @NotBlank(message = "Name is mandatory!")
    @Column(name="name")
    private String name;

    @Pattern(regexp = "[a-zA-Z-]+")
    @NotBlank(message = "Country is mandatory!")
    @Column(name="country")
    private String country;

    @Pattern(regexp = "[\\w -]+")
    @NotBlank(message = "Zipcode is mandatory!")
    @Column(name="zip_code")
    private String zipCode;

    @Pattern(regexp = "[a-zA-Z-]+")
    @NotBlank(message = "City name is mandatory!")
    @Column(name="city")
    private String city;

    @Column(name="average_score_peanut")
    private Double averageScorePeanut;

    @Column(name="average_score_egg")
    private Double averageScoreEgg;

    @Column(name="average_score_dairy")
    private Double averageScoreDairy;

    @Column(name="overall_score")
    private Double overallScore;
}
