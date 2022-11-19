package com.andy.portfolio.diningreviewapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reviews")
@NoArgsConstructor
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    private Boolean isApproved;

    private String userName;

    private Long restaurantId;

    private Integer peanutScore;

    private Integer eggScore;

    private Integer dairyScore;

    private String commentary;
}
