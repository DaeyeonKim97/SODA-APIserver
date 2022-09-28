package com.soda.apiserver.recommend.model.dto.request;

import com.soda.apiserver.review.model.entity.Review;

import java.util.ArrayList;
import java.util.List;

public class AiReviewListDTO {
    private int id;
    private List<AiReviewStoreDTO> store;

    public AiReviewListDTO() {
    }

    public AiReviewListDTO(int id, List<Review> reviewList){
        this.id = id;
        this.store = new ArrayList<>();
        for(Review review : reviewList){
            this.store.add(
                    new AiReviewStoreDTO(
                            review.getCategory().getName(),
                            review.getCreateDate(),
                            review.getGrade()
                    )
            );
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<AiReviewStoreDTO> getStore() {
        return store;
    }

    public void setStore(List<AiReviewStoreDTO> store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "AiReviewListDTO{" +
                "id=" + id +
                ", store=" + store +
                '}';
    }
}
