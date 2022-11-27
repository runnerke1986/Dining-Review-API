package com.andy.portfolio.diningreviewapi.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;


@Entity
@Table(name="review")
@RequiredArgsConstructor
@Getter @Setter
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    @Pattern(regexp = "[a-zA-Z-]+")
    @Column(name="user_name")
    @NonNull
    private String userName;


    @Column(name="restaurant_id")
    @NonNull
    private Long restaurantId;

    @Min(1)
    @Max(5)
    @Column(name="peanut_score")
    private Integer peanutScore;

    @Min(1)
    @Max(5)
    @Column(name="egg_score")
    private Integer eggScore;

    @Min(1)
    @Max(5)
    @Column(name="dairy_score")
    private Integer dairyScore;

    @Column(name="commentary")
    @NonNull
    private String commentary;

    @Column(name="review_status")
    private AdminReviewStatus adminReviewStatus;

    public Review(){
        this.setAdminReviewStatus(AdminReviewStatus.PENDING);
    }

    public Review(String userName, Long restaurantId, Integer peanutScore, Integer eggScore, Integer dairyScore, String commentary) {
        this.userName = userName;
        this.restaurantId = restaurantId;
        this.setAdminReviewStatus(AdminReviewStatus.PENDING);
    }
}
