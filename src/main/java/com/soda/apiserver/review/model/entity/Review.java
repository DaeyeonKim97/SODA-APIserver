package com.soda.apiserver.review.model.entity;

import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.file.model.entity.Attach;
import com.soda.apiserver.wish.model.entity.Restaurant;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="TBL_REVIEW")
@SequenceGenerator(
        name = "SEQ_REVIEW_ID_GENERATOR",
        sequenceName = "SEQ_REVIEW_ID",
        initialValue = 1,
        allocationSize = 1
)
public class Review {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_REVIEW_ID_GENERATOR"
    )
    @Column(name = "REVIEW_ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToOne
    @JoinColumn(name = "ATTACH_ID")
    private Attach attach;

    @Column(name = "REVIEW_GRADE")
    private int grade;

    @Column(name = "REVIEW_CONTENT")
    private String content;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    public Review() {
    }

    public Review(int id, User user, Restaurant restaurant, Category category, Attach attach, int grade, String content, Date createDate) {
        this.id = id;
        this.user = user;
        this.restaurant = restaurant;
        this.category = category;
        this.attach = attach;
        this.grade = grade;
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
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

    public Attach getAttach() {
        return attach;
    }

    public void setAttach(Attach attach) {
        this.attach = attach;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + user +
                ", restaurant=" + restaurant +
                ", category=" + category +
                ", attach=" + attach +
                ", grade=" + grade +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
