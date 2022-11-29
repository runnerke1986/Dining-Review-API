package com.andy.portfolio.diningreviewapi.repositories;

import com.andy.portfolio.diningreviewapi.model.AdminReviewStatus;
import com.andy.portfolio.diningreviewapi.model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    Iterable<Review> findByAdminReviewStatus(AdminReviewStatus status);
    Iterable<Review> findByRestaurantIdAndAdminReviewStatus(Long restaurantId, AdminReviewStatus status);
    List<Review> findByRestaurantIdAndEggScoreGreaterThan(Long restaurantId, Integer minScore);
    List<Review> findByRestaurantIdAndDairyScoreGreaterThan(Long restaurantId, Integer minScore);
    List<Review> findByRestaurantIdAndPeanutScoreGreaterThan(Long restaurantId, Integer minScore);
    List<Review> findByRestaurantId(Long id);
    Review findById(Long id);
}
