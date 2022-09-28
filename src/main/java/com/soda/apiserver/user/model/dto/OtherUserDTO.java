package com.soda.apiserver.user.model.dto;

import com.soda.apiserver.user.model.entity.User;
import com.soda.apiserver.file.model.entity.Attach;

public class OtherUserDTO {
    private int id;
    private String userName;
    private String email;
    private Attach profileImg;

    public OtherUserDTO() {
    }

    public OtherUserDTO(int id, String userName, String email, Attach profileImg) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.profileImg = profileImg;
    }

    public OtherUserDTO(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.profileImg = user.getProfileImg();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Attach getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(Attach profileImg) {
        this.profileImg = profileImg;
    }

    @Override
    public String toString() {
        return "OtherUserDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", profileImg=" + profileImg +
                '}';
    }
}
