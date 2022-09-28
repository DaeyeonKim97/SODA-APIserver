package com.soda.apiserver.follow.model.entity.embed;

import com.soda.apiserver.user.model.entity.User;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FollowId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "FOLLOWER_ID", referencedColumnName = "USER_ID")
    private User follower;

    public FollowId() {
    }

    public FollowId(User user, User follower) {
        this.user = user;
        this.follower = follower;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    @Override
    public String toString() {
        return "FollowId{" +
                "user=" + user +
                ", follower=" + follower +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowId followId = (FollowId) o;
        return Objects.equals(user, followId.user) && Objects.equals(follower, followId.follower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, follower);
    }
}
