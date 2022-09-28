package com.soda.apiserver.restaurant.dto;

import com.soda.apiserver.restaurant.service.UserFavoriteService;
import org.json.simple.JSONObject;

import java.util.List;

public class UserFavoriteListDto {
    private int userId;
    private List<JSONObject> favoriteCategoryList;

    public UserFavoriteListDto(){}

    public UserFavoriteListDto(int userId, List<JSONObject> favoriteCategoryList) {
        this.userId = userId;
        this.favoriteCategoryList = favoriteCategoryList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<JSONObject> getFavoriteCategoryList() {
        return favoriteCategoryList;
    }

    public void setFavoriteCategoryList(List<JSONObject> favoriteCategoryList) {
        this.favoriteCategoryList = favoriteCategoryList;
    }

    @Override
    public String toString() {
        return "UserFavoriteListDto{" +
                "userId=" + userId +
                ", favoriteCategoryList=" + favoriteCategoryList +
                '}';
    }
}
