package com.soda.apiserver.review.model.dto;

import com.soda.apiserver.user.model.dto.OtherUserDTO;
import com.soda.apiserver.review.model.entity.Comment;

import java.sql.Date;

public class CommentResponseDTO {
    private OtherUserDTO user;
    private String content;
    private Date date;


    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Comment comment){
        this.user = new OtherUserDTO(comment.getUser());
        this.content = comment.getContent();
        this.date = comment.getCreateDate();
    }

    public CommentResponseDTO(OtherUserDTO user, String content, Date date) {
        this.user = user;
        this.content = content;
        this.date = date;
    }

    public OtherUserDTO getUser() {
        return user;
    }

    public void setUser(OtherUserDTO user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CommentResponseDTO{" +
                "user=" + user +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
