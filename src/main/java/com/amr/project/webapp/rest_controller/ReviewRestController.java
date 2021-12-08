package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ReviewMapper;
import com.amr.project.exception.ExceptionInfo;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Review;
import com.amr.project.service.abstracts.*;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Arrays;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Veilas on 11/8/2021.
 * Class: ReviewRestController.java
 */

@AllArgsConstructor
@RestController
@RequestMapping("/api/review")
@Validated
public class ReviewRestController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private final ShopService shopService;
    private final ItemService itemService;
    private final RatingService ratingService;

    @PostMapping("/item")
    public ResponseEntity<?> addReviewItem(@Valid @RequestBody ReviewDto reviewDto) {
        Review review = reviewMapper.dtoToReview(reviewDto);
        review.setDate(Date.valueOf(LocalDate.now()));
        review.setUser(userService.getAuthorized());
        review.setItem(itemService.getItemName(reviewDto.getItemName()));
        review.setShop(null);
            List<Review> reviews = reviewService.getAll();
            for (Review r : reviews) {
                if (r.getItem() != null && r.getItem().getName().equals(reviewDto.getItemName())
                        && r.getUser().getUsername().equals(review.getUser().getUsername())) {
                    return ExceptionInfo.getExceptionUserAlreadyLeftReview();
                }
            }
        reviewService.persist(review);
        ratingService.setRating(review);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/shop")
    public ResponseEntity<?> addReviewShop(@Valid @RequestBody ReviewDto reviewDto) {
        Review review = reviewMapper.dtoToReview(reviewDto);
        review.setDate(Date.valueOf(LocalDate.now()));
        review.setShop(shopService.getShop(reviewDto.getShopName()));
        review.setUser(userService.getAuthorized());
        review.setItem(null);
            List<Review> reviews = reviewService.getAll();
            for (Review r : reviews) {
                if (r.getShop() != null && r.getShop().getName().equals(reviewDto.getShopName())
                        && r.getUser().getUsername().equals(review.getUser().getUsername())) {
                    return ExceptionInfo.getExceptionUserAlreadyLeftReview();
                }
        }
        reviewService.persist(review);
        ratingService.setRating(review);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/item/{id}")
    public ResponseEntity<Review> getReviewItem(@PathVariable("id") long id) {
        Review review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/items")
    public ResponseEntity<List<ReviewDto>> getAllReviewItems() {
        List<Review> reviews = setReviews("item");
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtos.add(reviewMapper.reviewToDto(review));
        }
        return ResponseEntity.ok(reviewDtos);
    }

    @GetMapping("/shops")
    public ResponseEntity<List<ReviewDto>> getAllReviewShops() {
        List<Review> reviews = setReviews("shop");
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtos.add(reviewMapper.reviewToDto(review));
        }
        return ResponseEntity.ok(reviewDtos);
    }

    private List<Review> setReviews(String itemOrShop) {
        List<Review> reviews = new ArrayList<>();
        for (Review review : reviewService.getAll()) {
            if (review.getItem() != null && itemOrShop.equals("item")) {
                reviews.add(review);
            } else if (review.getShop() != null && itemOrShop.equals("shop")) {
                reviews.add(review);
            }
        }
        return reviews;
    }

}
