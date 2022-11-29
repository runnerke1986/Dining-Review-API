package com.andy.portfolio.diningreviewapi.controllers;

import com.andy.portfolio.diningreviewapi.model.Restaurant;
import com.andy.portfolio.diningreviewapi.model.Review;
import com.andy.portfolio.diningreviewapi.repositories.RestaurantRepository;
import com.andy.portfolio.diningreviewapi.repositories.ReviewRepository;
import com.andy.portfolio.diningreviewapi.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private UserRepository userRepository;

    public ReviewController(final ReviewRepository reviewRepository, final RestaurantRepository restaurantRepository, final UserRepository userRepository){
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    //user submits a review with default status pending
    @PostMapping("/submit-review")
    @ResponseStatus(HttpStatus.CREATED)
    public Review submitReview(@Valid @RequestBody Review review) throws Exception {
        validateRestaurant(review.getRestaurantId());
        var userOptional = Optional.ofNullable(this.userRepository.findByName(review.getUserName()));
        if (userOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User not found.");
        }
        this.reviewRepository.save(review);
        return review;
    }

    @GetMapping("/")
    public Iterable<Review> getAllReviews(){
        return this.reviewRepository.findAll();
    }

    @GetMapping("restaurant/{id}")
    public List<Review> getReviewFromRestaurant(@PathVariable Long id){
        validateRestaurant(id);
        return this.reviewRepository.findByRestaurantId(id);
    }

    //query method for web application
    @GetMapping("/query-scores")
    public List<Review> queryReviewFromRestaurant(
            @RequestParam(name="restaurant-id", required = true) Long restaurantId,
            @RequestParam(name="dairy", required = false) Boolean dairy,
            @RequestParam(name="egg", required = false) Boolean egg,
            @RequestParam(name="peanut", required = false) Boolean peanut,
            @RequestParam(name="min-score", required = false) Integer minScore)
    {
        validateRestaurant(restaurantId);
        if(dairy){
            return this.reviewRepository.findByRestaurantIdAndDairyScoreGreaterThan(restaurantId, minScore);
        }else if(egg){
            return this.reviewRepository.findByRestaurantIdAndEggScoreGreaterThan(restaurantId, minScore);
        }else if(peanut){
            return this.reviewRepository.findByRestaurantIdAndPeanutScoreGreaterThan(restaurantId, minScore);
        } else {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "None of the allergies have been requested.");
        }
    }

    public Boolean validateRestaurant(Long restaurantId){
        Optional<Restaurant> restaurantOptional = Optional.ofNullable(this.restaurantRepository.findById(restaurantId));
        if (restaurantOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Restaurant not found!");
        }
        return true;
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
