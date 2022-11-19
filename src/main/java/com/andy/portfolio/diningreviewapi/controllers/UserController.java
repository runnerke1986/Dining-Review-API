package com.andy.portfolio.diningreviewapi.controllers;

import com.andy.portfolio.diningreviewapi.model.User;
import com.andy.portfolio.diningreviewapi.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @PostMapping("/registeruser")
    public User addUser(@RequestBody User user){
        Optional<User> userToRegisterOptional = Optional.ofNullable(this.userRepository.findByName(user.getName()));
        if (userToRegisterOptional.isPresent()){
            System.out.println("The provided username already exists in the database. Please enter a different one.");
            return null;
        }
        this.userRepository.save(user);
        return user;
    }

    //Test curl: curl http://localhost:6786/users/
    @GetMapping("/")
    public Iterable<User> getAllUsers(){
        return this.userRepository.findAll();
    }
}
