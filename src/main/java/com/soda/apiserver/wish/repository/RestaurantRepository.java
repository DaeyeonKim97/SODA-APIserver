package com.soda.apiserver.wish.repository;

import com.soda.apiserver.wish.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Restaurant findById(int id);
}
