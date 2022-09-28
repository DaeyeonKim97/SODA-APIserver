package com.soda.apiserver.recommend.model.dto.request;

public class AiStoreDTO {
    private String food_category;
    private int score;

    public AiStoreDTO() {
    }

    public AiStoreDTO(String food_category) {
        this.food_category = food_category;
        this.score = 5;
    }

    public AiStoreDTO(String food_category, int score) {
        this.food_category = food_category;
        this.score = score;
    }

    public String getFood_category() {
        return food_category;
    }

    public void setFood_category(String food_category) {
        this.food_category = food_category;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "AiStoreDTO{" +
                "food_category='" + food_category + '\'' +
                ", score=" + score +
                '}';
    }
}
