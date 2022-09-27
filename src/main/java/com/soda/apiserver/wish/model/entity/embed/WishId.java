package com.soda.apiserver.wish.model.entity.embed;

import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.wish.model.entity.Restaurant;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class WishId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID")
    private Restaurant restaurant;

    public WishId() {
    }

    public WishId(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishId wishId = (WishId) o;
        return Objects.equals(user, wishId.user) && Objects.equals(restaurant, wishId.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, restaurant);
    }

    @Override
    public String toString() {
        return "WishId{" +
                "user=" + user +
                ", restaurant=" + restaurant +
                '}';
    }
}
