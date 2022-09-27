package com.soda.apiserver.review.repository;

import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.review.model.entity.Review;
import com.soda.apiserver.wish.model.entity.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    int countReviewByUser(User user);
    List<Review> findReviewByUserOrderByCreateDateDesc(User user, Pageable pageable);
    Review findReviewByRestaurantAndUser(Restaurant restaurant, User user);
}
