package com.soda.apiserver.review.model.dto;

import com.soda.apiserver.user.model.dto.OtherUserDTO;
import com.soda.apiserver.review.model.entity.Review;
import com.soda.apiserver.wish.model.entity.Restaurant;

import java.util.Date;

public class ReviewResponseDTO {
    private int id;
    private OtherUserDTO user;
    private String categoryName;
    private Restaurant restaurant;
    private String imageSrc;
    private int grade;
    private String content;
    private Date createDate;

    public ReviewResponseDTO() {
    }

    public ReviewResponseDTO(int id, OtherUserDTO user, String categoryName, Restaurant restaurant, String imageSrc, int grade, String content, Date createDate) {
        this.id = id;
        this.user = user;
        this.categoryName = categoryName;
        this.restaurant = restaurant;
        this.imageSrc = imageSrc;
        this.grade = grade;
        this.content = content;
        this.createDate = createDate;
    }

    public ReviewResponseDTO(Review review){
        this.id = review.getId();
        this.user = new OtherUserDTO(review.getUser());
        this.categoryName = review.getCategory().getName();
        this.restaurant = review.getRestaurant();
        this.imageSrc = review.getAttach().getSavedPath();
        this.grade = review.getGrade();
        this.content = review.getContent();
        this.createDate = review.getCreateDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OtherUserDTO getUser() {
        return user;
    }

    public void setUser(OtherUserDTO user) {
        this.user = user;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    @Override
    public String toString() {
        return "ReviewResponseDTO{" +
                "id=" + id +
                ", user=" + user +
                ", categoryName='" + categoryName + '\'' +
                ", restaurant=" + restaurant +
                ", imageSrc='" + imageSrc + '\'' +
                ", grade=" + grade +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
