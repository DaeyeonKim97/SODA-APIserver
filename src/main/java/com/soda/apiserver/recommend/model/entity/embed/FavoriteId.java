package com.soda.apiserver.recommend.model.entity.embed;

import com.soda.apiserver.user.model.entity.User;
import com.soda.apiserver.review.model.entity.Category;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoriteId implements Serializable {
    @ManyToOne
    @JoinColumn(name="CATEGORY_ID")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public FavoriteId() {
    }

    public FavoriteId(Category category, User user) {
        this.category = category;
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(category, that.category) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, user);
    }

    @Override
    public String toString() {
        return "FavoriteId{" +
                "category=" + category +
                ", user=" + user +
                '}';
    }
}
