package com.example.buy.view;

import com.example.buy.entity.Car;

/**
 * @feature Visualize cars, search, deletion of cars, follow a car
 * @author Chenwei Niu
 */
public class CarView {

    private Car car;

    // the url string for the imageView
    private String carImage;

    // TextView 1
    private String carInformation;

    // TextView 2
    private int carPrice;

    // TextView 3
    private String carEngine;

    // TextView 4
    private int carOdometer;

    // TextView 5
    private String carBodyStyle;

    // TextView 6
    private String carTransmission;

    // TextView 7
    private String carLocation;

    //Like Image
    private int likeImage;

    // create constructor to set the values for all the parameters of the each single view
    public CarView(Car car) {
        carInformation = car.information;
        carImage = car.image;
        carPrice = car.price;
        carEngine = car.engine;
        carOdometer = car.odometer;
        carBodyStyle = car.bodyStyle;
        carTransmission = car.transmission;
        carLocation = car.location;
        this.car = car;
    }

    public String getCarImage() {
        return carImage;
    }

    public String getCarInformation() {
        return carInformation;
    }

    public int getCarPrice() {
        return carPrice;
    }

    public String getCarEngine() {
        return carEngine;
    }

    public int getCarOdometer() {
        return carOdometer;
    }

    public String getCarBodyStyle() {
        return carBodyStyle;
    }

    public String getCarTransmission() {
        return carTransmission;
    }

    public String getCarLocation() {
        return carLocation;
    }

    public int getLikeImage() {
        return likeImage;
    }

    public Car getCar() {
        return car;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public void setCarInformation(String carInformation) {
        this.carInformation = carInformation;
    }

    public void setCarPrice(int carPrice) {
        this.carPrice = carPrice;
    }

    public void setCarEngine(String carEngine) {
        this.carEngine = carEngine;
    }

    public void setCarOdometer(int carOdometer) {
        this.carOdometer = carOdometer;
    }

    public void setCarBodyStyle(String carBodyStyle) {
        this.carBodyStyle = carBodyStyle;
    }

    public void setCarTransmission(String carTransmission) {
        this.carTransmission = carTransmission;
    }

    public void setCarLocation(String carLocation) {
        this.carLocation = carLocation;
    }

    public void setLikeImage(int likeImage) {
        this.likeImage = likeImage;
    }
}
