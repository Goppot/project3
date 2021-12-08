package com.amr.project.util;

import com.amr.project.model.entity.Mail;
import com.amr.project.model.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackedEmailReview {

    public Mail trackedEmailReviewPersist(Review review) {
        Mail mail = new Mail();
        mail.setTo(review.getShop().getEmail());
        mail.setMessage("Магазину был оставлен отзыв: " + review.getShop().getName());
        return mail;
    }

    public Mail trackedEmailReviewDelete(Review review) {
        Mail mail = new Mail();
        mail.setTo(review.getShop().getEmail());
        mail.setMessage("Магазину был оставлен отзыв: " + review.getShop().getName());
        return mail;
    }


}
