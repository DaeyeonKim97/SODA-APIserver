package com.soda.apiserver.restaurant.dto;

public class OldReviewDto {
    private int oldReviewId;
    private int restaurantId;
    private String body;
    private String thumbnail;

    public OldReviewDto(){}

    public OldReviewDto(int oldReviewId, int restaurantId, String body, String thumbnail) {
        this.oldReviewId = oldReviewId;
        this.restaurantId = restaurantId;
        this.body = body;
        this.thumbnail = thumbnail;
    }

    public int getOldReviewId() {
        return oldReviewId;
    }

    public void setOldReviewId(int oldReviewId) {
        this.oldReviewId = oldReviewId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "oldReviewDto{" +
                "oldReviewId=" + oldReviewId +
                ", restaurantId=" + restaurantId +
                ", body='" + body + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }


}




