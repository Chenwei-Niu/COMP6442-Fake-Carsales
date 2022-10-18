package com.example.buy.entity;
//Author: Chenwei Niu
import com.example.buy.avltree.AvlTree;
import com.example.buy.parser.MyJsonReader;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Market<firstReadVolume> {
    MyJsonReader myJsonReader = new MyJsonReader();
    ArrayList<Car> cars = new ArrayList<>();
    char[] array = new char[90000];
    HashMap<String, AvlTree> Sites = new HashMap<>();


    InputStreamReader inputStreamReader;
    JsonReader jsonReader;
    BufferedReader carBufferedReader;
    BufferedReader userBufferedReader;
    final int firstReadVolume = 1000;
    int currentCount = 0;
    private static Market market = new Market();

    private Market () {

    }
    public static Market getMarket(){
        return market;
    }

    public ArrayList<Car> getCarArray(){
        return cars;
    }

    private void firstRetrieveCarData(){
        User user = new User("SystemAdminister","123456");
        try{
            String line;
            carBufferedReader.mark(999999);
            while(currentCount<firstReadVolume && (line = carBufferedReader.readLine())!=null){
                System.out.println(line);
                Car car = myJsonReader.parseOneLine(line);
                car.setId(currentCount);
                car.setUser(user);
                cars.add(car);
                currentCount++;
                if (currentCount%20 ==0){
                    carBufferedReader.reset();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
//        Sites.put("Benz", 1);
//        Sites.put("BMW", 2);
//        Sites.put("Ford", 3);
//        Sites.put("Honda", 4);
//        Sites.put("Mazda", 5);
//        Sites.put("Subaru", 6);
//        Sites.put("Kia", 7);
//        Type userListType = new TypeToken<ArrayList<Car>>(){}.getType();
//        try{
//            jsonReader.beginArray();
////            cars = gson.fromJson(jsonReader, userListType);
////            System.out.println(cars.size());
//            while (jsonReader.hasNext()) {
//                Car car = gson.fromJson(jsonReader,Car.class);
//                System.out.println(car);
//            }
//            jsonReader.endArray();
//        } catch (IOException e){
//            e.printStackTrace();
//        }


    }


    public void setCarBufferedReader(BufferedReader carBufferedReader){
        if (carBufferedReader != null) {
            this.carBufferedReader= carBufferedReader;
            firstRetrieveCarData();
        } else {
            return;
        }
    }
    public BufferedReader getCarBufferedReader(){
        return this.carBufferedReader;

    }
    public void setUserBufferedReader(BufferedReader userBufferedReader){
        if (userBufferedReader != null) {
            this.userBufferedReader= userBufferedReader;
        }
    }
    public BufferedReader userBufferedReader(){
        return this.userBufferedReader;

    }

    public void setInputStreamReader(InputStreamReader inputStreamReader) {
        this.inputStreamReader = inputStreamReader;
    }

    public void setJsonReader(JsonReader jsonReader) {
        this.jsonReader = jsonReader;
    }
}
