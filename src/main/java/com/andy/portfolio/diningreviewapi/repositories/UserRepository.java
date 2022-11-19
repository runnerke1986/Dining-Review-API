package com.andy.portfolio.diningreviewapi.repositories;

import com.andy.portfolio.diningreviewapi.model.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByName(String name);
}
