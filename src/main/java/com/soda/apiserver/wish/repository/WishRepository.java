package com.soda.apiserver.wish.repository;

import com.soda.apiserver.user.model.entity.User;
import com.soda.apiserver.wish.model.entity.Restaurant;
import com.soda.apiserver.wish.model.entity.Wish;
import com.soda.apiserver.wish.model.entity.embed.WishId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, WishId> {
    List<Wish> findWishByIdUser(User user);
    List<Wish> findWishByIdUserUserName(String username);
    List<Wish> findWishByIdRestaurant(Restaurant restaurant);
    Wish findWishByIdRestaurantAndIdUserUserName(Restaurant restaurant, String userName);
}
