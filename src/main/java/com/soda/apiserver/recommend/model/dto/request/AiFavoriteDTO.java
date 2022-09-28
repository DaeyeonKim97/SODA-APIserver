package com.soda.apiserver.recommend.model.dto.request;

import com.soda.apiserver.recommend.model.entity.Favorite;

import java.util.ArrayList;
import java.util.List;

public class AiFavoriteDTO {
    private int id;
    private List<AiStoreDTO> store;

    public AiFavoriteDTO() {
    }

    public AiFavoriteDTO(int id, List<Favorite> favoriteList) {
        this.id = id;
        this.store = new ArrayList<>();
        for(Favorite favorite : favoriteList){
            this.store.add(
                    new AiStoreDTO(
                            favorite.getId().getCategory().getName()
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

    public List<AiStoreDTO> getStore() {
        return store;
    }

    public void setStore(List<AiStoreDTO> store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "AiFavoriteDTO{" +
                "id=" + id +
                ", store=" + store +
                '}';
    }
}
