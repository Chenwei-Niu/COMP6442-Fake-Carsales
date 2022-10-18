package com.example.buy.parser;
//Author: Chenwei Niu
import com.example.buy.entity.Car;
import com.example.buy.entity.State;

public class MyJsonReader {
    public MyJsonReader() {
    }
    public Car parseOneLine(String line){
        String[] items = line.split("\": \"|\", \"");
        for (int i = 1; i < items.length;i=i+2 ){
            items[i] = items[i].replace("\"","");
        }
        items[items.length-1] = items[items.length-1].replaceAll("\\}\\,\\s|\\}\\]","");


        String information = items[1];
        String year = items[3];
        String price = items[5];
        String image = items[7];
        String odometer = items[9];
        String location = items[11];
        String bodyStyle = items[13];
        String transmission = items[15];
        String engine = items[17];
        String state = items[19];
        String brand = items[21];
        Car car = new Car(information,year,price,image,odometer,location,bodyStyle,transmission,engine, State.ONSALE,brand);
        System.out.println(car);
        return car;
    }
}
