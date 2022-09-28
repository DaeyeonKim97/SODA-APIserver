package com.soda.apiserver.restaurant.dto;

public class MenuDto {
    private int menuId;
    private int restaurantId;
    private String menuName;
    private String price;

    public MenuDto(){}

    public MenuDto(int menuId, int restaurantId, String menuName, String price) {
        this.menuId = menuId;
        this.restaurantId = restaurantId;
        this.menuName = menuName;
        this.price = price;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MenuDto{" +
                "menuId=" + menuId +
                ", restaurantId=" + restaurantId +
                ", menuName='" + menuName + '\'' +
                ", price=" + price +
                '}';
    }
}