package com.andy.portfolio.diningreviewapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="restaurant")
@Getter @Setter
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue
    Long id;
    @Column(unique = true)
    private String name;

    private String country;

    private String city;

    private Integer averageScorePeanut;

    private Integer averageScoreEgg;

    private Integer averageScoreDairy;

    private Integer overallScore;
}
