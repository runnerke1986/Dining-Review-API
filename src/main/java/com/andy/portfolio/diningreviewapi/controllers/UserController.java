package com.andy.portfolio.diningreviewapi.controllers;

import com.andy.portfolio.diningreviewapi.model.User;
import com.andy.portfolio.diningreviewapi.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
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
    public User addUser(@Valid @RequestBody User user) {
        if(this.userRepository.existsByName(user.getName())){
            throw new ResponseStatusException(HttpStatus.IM_USED, "The provided username already exists in the database. Please enter a different one.");
        }
        this.userRepository.save(user);
        return user;
    }

    @PutMapping("/update-profile/{userName}")
    public User modifyUser(@RequestBody User user, @PathVariable String userName) {
        var userToUpdateOptional = Optional.ofNullable(this.userRepository.findByName(userName));
        if (userToUpdateOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The provided username doesn't exist in the database. Please check your provided data or register.");
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
