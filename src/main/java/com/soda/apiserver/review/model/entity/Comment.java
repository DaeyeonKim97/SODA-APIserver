package com.soda.apiserver.review.model.entity;

import com.soda.apiserver.user.model.entity.User;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "TBL_COMMENT")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "COMMENT_ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "REVIEW_ID")
    private Review review;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    public Comment() {
    }

    public Comment(int id, User user, Review review, String content, Date createDate) {
        this.id = id;
        this.user = user;
        this.review = review;
        this.content = content;
        this.createDate = createDate;
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

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", review=" + review +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
