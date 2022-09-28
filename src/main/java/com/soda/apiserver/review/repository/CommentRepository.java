package com.soda.apiserver.review.repository;

import com.soda.apiserver.review.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    public List<Comment> findByReviewId(int reviewId);
    public int countByReviewId(int reviewId);
}
