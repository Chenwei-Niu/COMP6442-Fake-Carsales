package com.example.buy.parser;

import com.example.buy.entity.Car;
import com.example.buy.entity.State;

import java.io.BufferedReader;

public class MyJsonParser {
    public MyJsonParser() {
    }
    public Car parseOneLine(String line){
        String[] items = line.split("\": \"|\", \"");
        for (int i = 1; i < items.length;i=i+2 ){
            items[i] = items[i].replace("\"","");
        }
        items[items.length-1] = items[items.length-1].replace("}","");
        String information = items[1];
        int price = Integer.parseInt(items[3]);
        String image = items[5];
        int odometer = Integer.parseInt(items[7]);
        String location = items[9];
        String bodyStyle = items[11];
        String transmission = items[13];
        String engine = items[15];
        String state = items[17];
        String brand = items[19];
        Car car = new Car(information,price,image,odometer,location,bodyStyle,transmission,engine, State.ONSALE,brand);
        System.out.println(car);
        return car;
    }
}
