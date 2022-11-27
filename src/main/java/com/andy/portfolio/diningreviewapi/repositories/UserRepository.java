package com.andy.portfolio.diningreviewapi.repositories;

import com.andy.portfolio.diningreviewapi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByName(String name);
    Boolean existsByName(String name);
}
