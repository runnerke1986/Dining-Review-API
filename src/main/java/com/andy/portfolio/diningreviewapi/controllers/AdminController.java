package com.andy.portfolio.diningreviewapi.controllers;

import com.andy.portfolio.diningreviewapi.model.*;
import com.andy.portfolio.diningreviewapi.repositories.RestaurantRepository;
import com.andy.portfolio.diningreviewapi.repositories.ReviewRepository;
import com.andy.portfolio.diningreviewapi.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

//all actions in this endpoint should only be possible if the user executing them has been defined as isAdmin
@RestController
@RequestMapping("/admin")
public class AdminController {
    final private UserRepository userRepository;
    final private RestaurantRepository restaurantRepository;
    final private ReviewRepository reviewRepository;

    public AdminController(final UserRepository userRepository, final RestaurantRepository restaurantRepository, final ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PutMapping("/validate-review/{id}/{isAccepted}")
    private Review setApprovalStatusReview(@PathVariable Long id, @PathVariable Boolean isAccepted ) throws Exception {
        var reviewOptional = Optional.of(this.reviewRepository.findById(id));
        if (reviewOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        var review = reviewOptional.get();
        if(isAccepted){
            review.setAdminReviewStatus(AdminReviewStatus.ACCEPTED);
            this.reviewRepository.save(review);
            updateScores(review);
        }else{
            review.setAdminReviewStatus(AdminReviewStatus.REJECTED);
        }
        this.reviewRepository.save(review);
        return review;
    }

    //checks if the user is authorized (is admin?)
    public Boolean userIsAdmin(String name) throws Exception{
        if(!this.userRepository.existsByName(name)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The provided username doesn't exist in the database.");
        }
        User user = this.userRepository.findByName(name);
        return user.getIsAdmin();
    }

    // check if the user who submitted a review is known in the db or not
    public Boolean userSubmittedReviewExists(String userName){
        return this.userRepository.existsByName(userName);
    }

    //update scores from the restaurant from the accepted review
    private void updateScores(Review review) throws Exception{
        Optional <Restaurant> restaurantOptional = Optional.ofNullable(this.restaurantRepository.findById(review.getRestaurantId()));
        if(restaurantOptional.isEmpty()){
            review.setAdminReviewStatus(AdminReviewStatus.REJECTED);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The provided restaurant doesn't exist in the database.");
        }
        Restaurant restaurantToUpdate = restaurantOptional.get();
        Iterable<Review> allAcceptedReviews = this.reviewRepository.findByRestaurantIdAndAdminReviewStatus(review.getRestaurantId(),AdminReviewStatus.ACCEPTED);
        Integer sumDairyScore = 0;
        Integer sumEggScore = 0;
        Integer sumPeanutScore = 0;
        Integer countReviews  = 0;
        for(Review acceptedReview : allAcceptedReviews){
            if(acceptedReview.getDairyScore() != null){
                sumDairyScore += acceptedReview.getDairyScore();
            }
            if(acceptedReview.getEggScore() != null){
                sumEggScore += acceptedReview.getEggScore();
            }
            if(acceptedReview.getPeanutScore() != null){
                sumPeanutScore += acceptedReview.getPeanutScore();
            }
            countReviews++;
        }
        restaurantToUpdate.setAverageScoreEgg(Double.valueOf(sumEggScore / countReviews));
        restaurantToUpdate.setAverageScoreDairy(Double.valueOf(sumDairyScore / countReviews));
        restaurantToUpdate.setAverageScorePeanut(Double.valueOf(sumPeanutScore / countReviews));
        restaurantToUpdate.setOverallScore ((restaurantToUpdate.getAverageScoreDairy() + restaurantToUpdate.getAverageScoreEgg() + restaurantToUpdate.getAverageScorePeanut()) / 3);
        this.restaurantRepository.save(restaurantToUpdate);
    }

    //Returns a list of reviews with status pending
    @GetMapping("/reviews-pending")
    public Iterable<Review> getPendingReviews(){
        return this.reviewRepository.findByAdminReviewStatus(AdminReviewStatus.PENDING);
    }

    @ResponseStatus(HttpStatus.GONE)
    @DeleteMapping("/delete-profile/{userName}")
    public User deleteProfile(@PathVariable String userName) throws Exception{
        Optional<User> userOptional = Optional.ofNullable(this.userRepository.findByName(userName));
        if (!userSubmittedReviewExists(userName)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The provided username was already removed from the database or never existed.");
        }
        User userToDelete = userOptional.get();
        this.userRepository.delete(userToDelete);
        return userToDelete;
    }

    @ResponseStatus(HttpStatus.GONE)
    @DeleteMapping("/delete-restaurant/{id}")
    public Restaurant deleteRestaurant(@PathVariable Long id) throws Exception {
        Optional<Restaurant> restaurantOptional = Optional.ofNullable(this.restaurantRepository.findById(id));
        if (restaurantOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The provided restaurant was already removed from the database or never existed.");
        }
        Restaurant restaurantToDelete = restaurantOptional.get();
        //delete the reviews from the restaurant before deleting the restaurant itself
        deleteReviewsFromRestaurant(id);
        this.restaurantRepository.delete(restaurantToDelete);
        return restaurantToDelete;
    }

    public void deleteReviewsFromRestaurant(Long restaurantId) throws Exception{
        Optional<Restaurant> restaurantOptional = Optional.ofNullable(this.restaurantRepository.findById(restaurantId));
        if (restaurantOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The provided restaurant was already removed from the database or never existed.");
        }
        List<Review> reviewsToDelete = this.reviewRepository.findByRestaurantId(restaurantId);
        for(Review reviewToDelete : reviewsToDelete){
            this.reviewRepository.delete(reviewToDelete);
        }
    }
}
