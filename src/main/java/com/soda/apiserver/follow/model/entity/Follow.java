package com.soda.apiserver.follow.model.entity;

import com.soda.apiserver.follow.model.entity.embed.FollowId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "TBL_FOLLOW")
public class Follow {
    @EmbeddedId
    private FollowId id;
    @Column(name = "FOLLOW_DATE", nullable = false)
    private Date followDate;

    public Follow() {
    }

    public Follow(FollowId id, Date followDate) {
        this.id = id;
        this.followDate = followDate;
    }

    public FollowId getId() {
        return id;
    }

    public void setId(FollowId id) {
        this.id = id;
    }

    public Date getFollowDate() {
        return followDate;
    }

    public void setFollowDate(Date followDate) {
        this.followDate = followDate;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", followDate=" + followDate +
                '}';
    }
}
