package com.andy.portfolio.diningreviewapi.controllers;

import com.andy.portfolio.diningreviewapi.model.User;
import com.andy.portfolio.diningreviewapi.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register-user")
    public User addUser(@RequestBody User user) throws Exception {
        if(this.userRepository.existsByName(user.getName())){
            throw new ResponseStatusException(HttpStatus.IM_USED, "The provided username already exists in the database. Please enter a different one.");
        }
        this.userRepository.save(user);
        return user;
    }

    @PutMapping("/update-profile/{userName}")
    public User modifyUser(@RequestBody User user, @PathVariable String userName) throws Exception {
        var userToUpdateOptional = Optional.ofNullable(this.userRepository.findByName(userName));
        if (userToUpdateOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The provided username doesn't exist in the database. Please check your provided data or register.");
        }
        var userToUpdate = userToUpdateOptional.get();

        //Update fields only if the parameter is given.
        //updating name and id excluded because of unique properties and isAdmin excluded because of security reasons
        if (user.getCity() != null) {userToUpdate.setCity(user.getCity());}
        if (user.getState() != null) {userToUpdate.setState(user.getState());}
        if (user.getZipCode() != null) {userToUpdate.setZipCode(user.getZipCode());}
        if (user.getHasEggAllergy() != null) {userToUpdate.setHasEggAllergy(user.getHasEggAllergy());}
        if (user.getHasDairyAllergy() != null) {userToUpdate.setHasDairyAllergy(user.getHasDairyAllergy());}
        if (user.getHasPeanutAllergy() != null) {userToUpdate.setHasPeanutAllergy(user.getHasPeanutAllergy());}

        this.userRepository.save(userToUpdate);
        return userToUpdate;
    }

    //Test curl: curl http://localhost:6786/user/
    @GetMapping("/")
    public Iterable<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    //Test curl: curl http://localhost:6786/user/profile?name=Andy%20De%20Metter
    @GetMapping("/profile")
    public User getUserProfileDetails(@RequestParam(name="name") String name){
        return this.userRepository.findByName(name);
    }
}
