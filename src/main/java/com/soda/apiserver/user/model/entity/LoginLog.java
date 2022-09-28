package com.soda.apiserver.user.model.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "TBL_LOGIN_LOG")
public class LoginLog {
    @Id
    @GeneratedValue
    @Column(name = "LOGIN_LOG_ID")
    private int id;
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;
    @NotNull
    @Column(name = "LOGIN_DATE")
    private Date loginDate;

    public LoginLog() {}
    public LoginLog(int id, User user, Date loginDate) {
        this.id = id;
        this.user = user;
        this.loginDate = loginDate;
    }

    public LoginLog(User user, Date loginDate){
        this(-1, user, loginDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    @Override
    public String toString() {
        return "LoginLog{" +
                "id=" + id +
                ", user=" + user +
                ", loginDate=" + loginDate +
                '}';
    }
}
