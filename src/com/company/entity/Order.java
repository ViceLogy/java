package com.company.entity;

import java.util.Objects;

public class Order {

    private int id;

    private int userId;

    private String description;

    private int price;

    public Order() {

    }

    public Order(int id, int userId , String description, int price) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.price = price;
    }

    public Order(int userId ,String description, int price) {
        this.userId = userId;
        this.description = description;
        this.price = price;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && userId == order.userId && price == order.price && Objects.equals(description, order.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, description, price);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
