package com.soda.apiserver.recommend.model.entity;

import com.soda.apiserver.recommend.model.entity.embed.FavoriteId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_FAVORITE")
public class Favorite {
    @EmbeddedId
    private FavoriteId id;

    public Favorite() {
    }

    public Favorite(FavoriteId id) {
        this.id = id;
    }

    public FavoriteId getId() {
        return id;
    }

    public void setId(FavoriteId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                '}';
    }
}
