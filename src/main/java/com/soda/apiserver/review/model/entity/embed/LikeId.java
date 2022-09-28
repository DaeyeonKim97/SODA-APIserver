package com.soda.apiserver.review.model.entity.embed;

import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.review.model.entity.Review;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LikeId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "REVIEW_ID")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public LikeId() {
    }

    public LikeId(Review review, User user) {
        this.review = review;
        this.user = user;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeId likeId = (LikeId) o;
        return Objects.equals(review, likeId.review) && Objects.equals(user, likeId.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(review, user);
    }

    @Override
    public String toString() {
        return "LikeId{" +
                "review=" + review +
                ", user=" + user +
                '}';
    }
}
