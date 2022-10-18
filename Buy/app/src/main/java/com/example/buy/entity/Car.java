package com.example.buy.entity;
//Author: Chenwei Niu Canxuan Gang
import com.example.buy.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Car implements Serializable, Comparable<Car>{
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
    public ArrayList<User> favoriteUsers = new ArrayList<>();  // could be deleted if not implement this function

    public Car() {
    }

    public Car(String information, int year, int price, String image, int odometer, String location, String bodyStyle, String transmission, String engine, State state, String brand) {
        this.information = information;
        this.year=year;
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

    public Car(String information, String year, String price, String image, String odometer, String location, String bodyStyle, String transmission, String engine, State state, String brand) {
        this.information = information;
        this.engine = engine;
        this.image = image;
        this.location = location;
        this.bodyStyle = bodyStyle;
        this.state = state;
        this.transmission = transmission;
        this.brand = brand;
        if (Utils.isANumber(year)){
            this.year=Integer.parseInt(year);
        } else {
            this.year = -1; // -1 means this attribute is unavailable
        }

        if (Utils.isANumber(price)){
            this.price=Integer.parseInt(price);
        } else {
            this.price = -1;
        }

        if (Utils.isANumber(odometer)){
            this.odometer=Integer.parseInt(odometer);
        } else {
            this.odometer = -1;
        }
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
    public void notifyUsers()
    {
        for(User user: favoriteUsers) {
            this.user.update(this);
            favoriteUsers.remove(user);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPrice(int price) {
        this.price = price;
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

    @Override
    public int compareTo(Car car) {
        if (this.price>car.price){
            return 1;
        } else if (this.price < car.price){
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return id == car.id && year == car.year && price == car.price && odometer == car.odometer && Objects.equals(information, car.information) && Objects.equals(image, car.image) && Objects.equals(location, car.location) && Objects.equals(bodyStyle, car.bodyStyle) && Objects.equals(transmission, car.transmission) && Objects.equals(engine, car.engine) && state == car.state && Objects.equals(brand, car.brand) && Objects.equals(user, car.user) && Objects.equals(getFavoriteUsers(), car.getFavoriteUsers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, information, year, price, image, odometer, location, bodyStyle, transmission, engine, state, brand, user, getFavoriteUsers());
    }
}
