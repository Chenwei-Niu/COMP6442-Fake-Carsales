package com.example.buy.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Car implements Serializable {
    public int id;
    public String information;
    public int year;
    public int price;
    public String image;
    public int odometer;
    public String location;
    public String bodyStyle;
    public String transmission;
    public String engine;
    public State state;
    public String brand;
    public User user;
    public ArrayList<User> favoriteUsers;  // could be deleted if not implement this function


    public Car(String information, int price, String image, int odometer, String location, String bodyStyle, String transmission, String engine, State state, String brand) {
        this.information = information;
        this.price = price;
        this.engine = engine;
        this.odometer = odometer;
        this.image = image;
        this.location = location;
        this.bodyStyle = bodyStyle;
        this.state = state;
        this.transmission = transmission;
        this.brand = brand;
    }

    public Car(int id, String information,int year, int price, String image, int odometer, String location, String bodyStyle, String transmission, String engine, State state, String brand, User user) {
        this.id = id;
        this.information = information;
        this.year = year;
        this.price = price;
        this.engine = engine;
        this.odometer = odometer;
        this.image = image;
        this.location = location;
        this.bodyStyle = bodyStyle;
        this.state = state;
        this.transmission = transmission;
        this.brand = brand;
        this.user = user;
    }

    public ArrayList<User> getFavoriteUsers() {
        return favoriteUsers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", information='" + information + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", odometer=" + odometer +
                ", location='" + location + '\'' +
                ", bodyStyle='" + bodyStyle + '\'' +
                ", transmission='" + transmission + '\'' +
                ", engine='" + engine + '\'' +
                ", state=" + state +
                ", brand='" + brand + '\'' +
                ", user=" + user +
                ", favoriteUsers=" + favoriteUsers +
                '}';
    }
}
