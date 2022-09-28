package com.soda.apiserver.review.model.dto;

import com.soda.apiserver.user.model.dto.OtherUserDTO;
import com.soda.apiserver.user.model.entity.User;

import java.util.Date;

public class LikeResponseDTO {
    private OtherUserDTO user;
    private Date date;

    public LikeResponseDTO() {
    }

    public LikeResponseDTO(OtherUserDTO user, Date date) {
        this.user = user;
        this.date = date;
    }

    public LikeResponseDTO(User user, java.sql.Date date){
        this.user = new OtherUserDTO(user);
        this.date = new java.util.Date(date.getTime());
    }

    public OtherUserDTO getUser() {
        return user;
    }

    public void setUser(OtherUserDTO user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "LikeResponseDTO{" +
                "user=" + user +
                ", date=" + date +
                '}';
    }
}
