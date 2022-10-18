package com.example.buy.entity;
//Author: Chenwei Niu

import android.content.Context;

import com.example.buy.parser.MyJsonReader;
import com.example.buy.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Market<firstReadVolume> {
    MyJsonReader myJsonReader = new MyJsonReader();
    ArrayList<Car> cars = new ArrayList<>();
    private Context context;
    HashMap<String,Integer> map = new HashMap<>();



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

    public void firstRetrieveCarData(){
        User user = new User("test@gmail.com","123456");
        String jsonFileString = Utils.getJsonFromAssets(context,"kia.json");
        String[] lines= jsonFileString.split("\\n");


        for (int i =0;i<lines.length;i++){

            Car car = myJsonReader.parseOneLine(lines[i]);
            car.setId(i);
            car.setUser(user);
            cars.add(car);
        }

        /*
         Below is the Gson code, the reason why we don't use is that our
         String exceeds the length that gson.fromJson can handle

                Type listUserType = new TypeToken<ArrayList<Car>>() { }.getType();
                cars = gson.fromJson(jsonFileString, listUserType);
                for (int i =0;i<cars.size();i++){
                    cars.get(i).setId(i);
                    cars.get(i).setUser(user);
                }

        */


    }


    public void setContext(Context context) {
        this.context = context;
    }

}
