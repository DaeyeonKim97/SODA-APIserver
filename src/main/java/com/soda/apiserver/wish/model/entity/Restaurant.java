package com.soda.apiserver.wish.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "TBL_RESTAURANT")
public class Restaurant {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "BIZHOUR_INFO")
    private String bizhourInfo;
    @Column(name = "X")
    private int x;
    @Column(name = "Y")
    private int y;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "DESCRIPTION")
    private String desc;
    @Column(name = "IMAGE_PATH")
    private String imagePath;
    @Column(name = "VIEWS", nullable = true)
    private Integer views;

    public Restaurant() {
    }

    public Restaurant(int id, String name, String address, String bizhourInfo, int x, int y, String phone, String desc, String imagePath, int views) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.bizhourInfo = bizhourInfo;
        this.x = x;
        this.y = y;
        this.phone = phone;
        this.desc = desc;
        this.imagePath = imagePath;
        this.views = views;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBizhourInfo() {
        return bizhourInfo;
    }

    public void setBizhourInfo(String bizhourInfo) {
        this.bizhourInfo = bizhourInfo;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", bizhourInfo='" + bizhourInfo + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", phone='" + phone + '\'' +
                ", desc='" + desc + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", views=" + views +
                '}';
    }
}
