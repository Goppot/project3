package com.amr.project.webapp.rest_controller.moderator_controller;

import com.amr.project.converter.ReviewMapper;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Review;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ReviewService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"API для работы с отзывами на странице модератора"})
@RestController
@RequestMapping("/moderator/api/reviews")
public class ReviewModeratorController {
    ReviewService reviewService;
    UserService userService;
    ShopService shopService;
    ItemService itemService;
    ReviewMapper reviewMapper;

    @Autowired
    public ReviewModeratorController(ReviewService reviewService, UserService userService, ShopService shopService,
                                     ItemService itemService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.shopService = shopService;
        this.itemService = itemService;
        this.reviewMapper = reviewMapper;
    }

    @ApiOperation(value = "Отправляет все отзывы не прошедшие модерацию на фронт")
    @GetMapping("/getUnmoderatedReviews")
    public ResponseEntity<List<ReviewDto>> getUnmodetaredReviews() {
        return ResponseEntity.ok(reviewService.
                getUnmoderatedReviews()
                .stream()
                .map(reviewMapper::reviewToDto)
                .collect(Collectors.toList()));
    }

    @ApiOperation(value = "Отправляет один не прошедший модерацию отзыв на фронт по id")
    @GetMapping("/getOneUnmoderatedReview/{id}")
    public ResponseEntity<ReviewDto> getOneUnmoderatedReview(@PathVariable("id") Long id) {
        Review review = reviewService.getByKey(id);
        if (review == null || review.isModerated()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(reviewMapper.reviewToDto(review));
        }
    }

    @ApiOperation(value = "Получает измененный отзыв из фронта и обновляет в базе данных")
    @PutMapping("/editReview")
    public ResponseEntity<ReviewDto> editReview(@RequestBody ReviewDto reviewDto) {
        if (reviewService.existsById(reviewDto.getId())) {
            Review review = reviewMapper.dtoToReview(reviewDto);
            review.setUser(userService.getByKey(reviewDto.getUserId()));
            review.setShop(shopService.getByKey(reviewDto.getShopId()));
            review.setItem(itemService.getByKey(reviewDto.getItemId()));
            reviewService.update(review);
            return ResponseEntity.ok(reviewMapper.reviewToDto(review));
        }
        return ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "возвращает на фронт количество не прошедних модерацию отзывов для счетчика")
    @GetMapping("/getUnmoderatedReviewsCount")
    public ResponseEntity<Long> getUnmoderatedReviewsCount() {
        return ResponseEntity.ok((long) reviewService
                .getUnmoderatedReviews()
                .size());
    }
}