package com.andy.portfolio.diningreviewapi.repositories;

import com.andy.portfolio.diningreviewapi.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    Boolean existsByNameAndZipCode(String name, String zipCode);
    Boolean existsById(Long id);
    Restaurant findById(Long id);
    List<Restaurant> findByZipCodeAndAverageScoreEggNotNullOrAverageScoreDairyNotNullOrAverageScorePeanutNotNullOrderByZipCodeDesc(String zipCode);
    List<Restaurant> findByZipCodeOrderByNameAsc(String zipCode);
    List<Restaurant> findByZipCodeOrderByNameDesc(String zipCode);

    List<Restaurant> findByCityOrderByNameAsc(String city);
    List<Restaurant> findByCityOrderByNameDesc(String city);
    Restaurant findByName(String name);

    List<Restaurant> findByCountryOrderByNameAsc(String country);
    List<Restaurant> findByCountryOrderByNameDesc(String country);

    //TODO: These custom queries will be coded when the front-end is being developed
    List<Restaurant> OrderByNameAsc();
    List<Restaurant> OrderByNameDesc();

    List<Restaurant> OrderByCountryAsc();
    List<Restaurant> OrderByCountryDesc();
}
