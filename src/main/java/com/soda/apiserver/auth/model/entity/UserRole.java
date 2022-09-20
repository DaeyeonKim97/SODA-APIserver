package com.soda.apiserver.auth.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TBL_USER_ROLE")
public class UserRole implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    public UserRole() {
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "user=" + user +
                ", role=" + role +
                '}';
    }
}
