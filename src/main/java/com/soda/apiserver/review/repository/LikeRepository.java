package com.soda.apiserver.review.repository;

import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.review.model.entity.Like;
import com.soda.apiserver.review.model.entity.embed.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
    int countByIdReviewId(int reviewId);
    List<Like> findLikeByIdReviewId(int reviewId);
    Like findLikeById(LikeId id);
}
