package com.andy.portfolio.diningreviewapi.repositories;

import com.andy.portfolio.diningreviewapi.model.Restaurant;
import org.springframework.data.repository.CrudRepository;


public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
}
