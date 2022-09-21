package com.soda.apiserver.auth.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.soda.apiserver.file.model.entity.Attach;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="TBL_USER")
@SequenceGenerator(
        name = "SEQ_USER_ID_GENERATOR",
        sequenceName = "SEQ_USER_ID",
        initialValue = 100,
        allocationSize = 1
)
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_USER_ID_GENERATOR"
    )
    @Column(name = "USER_ID")
    private int id; // 인덱스
    @Column(name = "USERNAME", unique = true, nullable = false)
    private String userName; //아이디
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;
    @Column(name = "JOIN_DATE", updatable = false, nullable = false)
    private Date joinDate;
    @Column(name = "IS_DELETED", nullable = false)
    private String isDeleted;
    @Column(name = "DELETED_DATE")
    private Date deletedDate;

    @OneToOne
    @JoinColumn(name = "ATTACH_ID")
    private Attach profileImg;

    public User() {
    }

    public User(int id, String userName, String password, String email, Date joinDate, String isDeleted, Date deletedDate, Attach profileImg) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.joinDate = joinDate;
        this.isDeleted = isDeleted;
        this.deletedDate = deletedDate;
        this.profileImg = profileImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Attach getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(Attach profileImg) {
        this.profileImg = profileImg;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", joinDate=" + joinDate +
                ", isDeleted='" + isDeleted + '\'' +
                ", deletedDate=" + deletedDate +
                ", profileImg=" + profileImg +
                '}';
    }
}
