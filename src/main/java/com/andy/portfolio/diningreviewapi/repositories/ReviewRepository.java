package com.andy.portfolio.diningreviewapi.repositories;

import com.andy.portfolio.diningreviewapi.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
