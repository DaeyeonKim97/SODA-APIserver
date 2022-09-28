package com.soda.apiserver.recommend.model.dto.response;

import com.soda.apiserver.review.model.entity.Category;

import java.util.List;

public class AiResponseDTO {
    private Category category;
    private List<Integer> restaurantIdList;

    public AiResponseDTO() {
    }

    public AiResponseDTO(Category category, List<Integer> restaurantIdList) {
        this.category = category;
        this.restaurantIdList = restaurantIdList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Integer> getRestaurantIdList() {
        return restaurantIdList;
    }

    public void setRestaurantIdList(List<Integer> restaurantIdList) {
        this.restaurantIdList = restaurantIdList;
    }

    @Override
    public String toString() {
        return "AiResponseDTO{" +
                "category=" + category +
                ", restaurantIdList=" + restaurantIdList +
                '}';
    }
}
