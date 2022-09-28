package com.soda.apiserver.restaurant.dto;


public class RestaurantDto {
    private int id;
    private String name;
    private String address;
    private String bizhorInfo;
    private double x;
    private double y;
    private String phone;
    private String description;
    private String image;


    private int views;

    public RestaurantDto(int id, String name, String address, String bizhorInfo, double x, double y, String phone, String description, String image, int views) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.bizhorInfo = bizhorInfo;
        this.x = x;
        this.y = y;
        this.phone = phone;
        this.description = description;
        this.image = image;
        this.views = views;
    }

    public RestaurantDto(){}

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

    public String getBizhorInfo() {
        return bizhorInfo;
    }

    public void setBizhorInfo(String bizhorInfo) {
        this.bizhorInfo = bizhorInfo;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "RestaurantDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", bizhorInfo='" + bizhorInfo + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", views=" + views +
                '}';
    }

}


