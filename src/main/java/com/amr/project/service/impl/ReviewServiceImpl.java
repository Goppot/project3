package com.amr.project.service.impl;


import com.amr.project.dao.impl.ReviewDaoImpl;
import com.amr.project.model.entity.Review;
import com.amr.project.service.abstracts.ReviewService;
import com.amr.project.service.email.EmailSenderService;
import com.amr.project.util.TrackedEmailReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl extends ReadWriteServiceImpl<Review, Long> implements ReviewService {

    private ReviewDaoImpl reviewDao;

    private EmailSenderService emailSenderService;

    private TrackedEmailReview trackedEmailReview;


    @Autowired
    public ReviewServiceImpl(TrackedEmailReview trackedEmailService, ReviewDaoImpl reviewDao, EmailSenderService emailSenderService) {
        super(reviewDao);
        this.reviewDao = reviewDao;
        this.trackedEmailReview = trackedEmailService;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public List<Review> getUnmoderatedReviews() {
        return reviewDao.getUnmoderatedReviews();
    }

    @Override
    public List<Review> getModeratedReviews() {
        return reviewDao.getModeratedReviews();
    }

    @Override
    public Review getReviewById(long id) {
        return reviewDao.getByKey(id);
    }

    @Override
    @Transactional
    public void persist(Review review) {
        if (review.getShop() != null) {
            emailSenderService.sendSimpleEmail(trackedEmailReview.trackedEmailReviewPersist(review));
        }
        reviewDao.persist(review);

    }

    @Override
    @Transactional
    public void delete(Review review) {
        emailSenderService.sendSimpleEmail(trackedEmailReview.trackedEmailReviewDelete(review));
        reviewDao.delete(review);

    }
}
