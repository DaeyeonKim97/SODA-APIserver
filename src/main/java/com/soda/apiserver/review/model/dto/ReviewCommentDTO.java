package com.soda.apiserver.review.model.dto;

public class ReviewCommentDTO {
    private String comment;

    public ReviewCommentDTO() {
    }

    public ReviewCommentDTO(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ReviewCommentDTO{" +
                "comment='" + comment + '\'' +
                '}';
    }
}
