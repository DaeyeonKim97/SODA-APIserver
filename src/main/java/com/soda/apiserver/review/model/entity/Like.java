package com.soda.apiserver.review.model.entity;

import com.soda.apiserver.review.model.entity.embed.LikeId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "TBL_LIKE")
public class Like {
    @EmbeddedId
    private LikeId id;
    @Column(name = "LIKE_DATE")
    private Date likeDate;

    public Like() {
    }

    public Like(LikeId id, Date likeDate) {
        this.id = id;
        this.likeDate = likeDate;
    }

    public LikeId getId() {
        return id;
    }

    public void setId(LikeId id) {
        this.id = id;
    }

    public Date getLikeDate() {
        return likeDate;
    }

    public void setLikeDate(Date likeDate) {
        this.likeDate = likeDate;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", likeDate=" + likeDate +
                '}';
    }
}
