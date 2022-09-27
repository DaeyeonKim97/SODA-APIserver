package com.soda.apiserver.wish.model.entity;

import com.soda.apiserver.wish.model.entity.embed.WishId;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="TBL_WISH")
public class Wish {
    @EmbeddedId
    private WishId id;

    @Column(name = "WISH_DATE", nullable = false)
    private Date wishDate;


    public Wish() {
    }

    public Wish(WishId id, Date wishDate) {
        this.id = id;
        this.wishDate = wishDate;
    }

    public WishId getId() {
        return id;
    }

    public void setId(WishId id) {
        this.id = id;
    }

    public Date getWishDate() {
        return wishDate;
    }

    public void setWishDate(Date wishDate) {
        this.wishDate = wishDate;
    }

    @Override
    public String toString() {
        return "Wish{" +
                "id=" + id +
                ", wishDate=" + wishDate +
                '}';
    }
}
