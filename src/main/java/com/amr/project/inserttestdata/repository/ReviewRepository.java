package com.amr.project.inserttestdata.repository;

import com.amr.project.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByUserId(Long id);
}
