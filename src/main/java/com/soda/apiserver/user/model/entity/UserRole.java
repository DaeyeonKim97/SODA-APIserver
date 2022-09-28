package com.soda.apiserver.user.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TBL_USER_ROLE")
@SequenceGenerator(
        name = "SEQ_USER_ROLE_ID_GENERATOR",
        sequenceName = "SEQ_USER_ROLE_ID",
        initialValue = 1,
        allocationSize = 1
)
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_USER_ROLE_ID_GENERATOR"
    )
    @Column(name = "USER_ROLE_ID")
    private int UserRoleId;

    @ManyToOne
    @JoinColumn(name="USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
    private Role role;

    public UserRole() {
    }

    public UserRole(int userRoleId, User user, Role role) {
        UserRoleId = userRoleId;
        this.user = user;
        this.role = role;
    }

    public int getUserRoleId() {
        return UserRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        UserRoleId = userRoleId;
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
                "UserRoleId='" + UserRoleId + '\'' +
                ", user=" + user.getUserName() +
                ", role=" + role.getRoleName() +
                '}';
    }
}
