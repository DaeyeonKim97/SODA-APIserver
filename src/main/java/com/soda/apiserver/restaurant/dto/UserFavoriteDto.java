package com.soda.apiserver.restaurant.dto;

public class UserFavoriteDto {
    private int userId;
    private int categoryId;

    public UserFavoriteDto(){}
    public UserFavoriteDto(int userId, int categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserFavoriteDto{" +
                "userId=" + userId +
                ", categoryId=" + categoryId +
                '}';
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
