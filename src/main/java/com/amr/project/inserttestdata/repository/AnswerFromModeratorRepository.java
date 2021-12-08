package com.amr.project.inserttestdata.repository;

import com.amr.project.model.entity.AnswerFromModerator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerFromModeratorRepository extends JpaRepository<AnswerFromModerator, Long> {
}
