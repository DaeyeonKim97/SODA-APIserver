package com.soda.apiserver.wish.model.dto;

public class RestaurantIdDTO {
    private int restaurantId;

    public RestaurantIdDTO() {
    }

    public RestaurantIdDTO(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "RestaurantIdDTO{" +
                "restaurantId=" + restaurantId +
                '}';
    }
}
