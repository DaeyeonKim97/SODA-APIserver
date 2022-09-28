package com.soda.apiserver.recommend.model.dto;

import java.util.Date;

public class AiReviewStoreDTO {
    private String food_category;
    private Date date;
    private int score;

    public AiReviewStoreDTO() {
    }

    public AiReviewStoreDTO(String food_category, Date date, int score) {
        this.food_category = food_category;
        this.date = date;
        this.score = score;
    }

    public String getFood_category() {
        return food_category;
    }

    public void setFood_category(String food_category) {
        this.food_category = food_category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "AiReviewStoreDTO{" +
                "food_category='" + food_category + '\'' +
                ", date=" + date +
                ", score=" + score +
                '}';
    }
}
