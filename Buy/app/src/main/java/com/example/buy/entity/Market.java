package com.example.buy.entity;
//Author: Chenwei Niu

import android.content.Context;

import com.example.buy.avltree.AvlTree;
import com.example.buy.parser.MyJsonReader;
import com.example.buy.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Market<firstReadVolume> implements Runnable{
    MyJsonReader myJsonReader = new MyJsonReader();
    ArrayList<Car> cars = new ArrayList<>();
    private Context context;
    private String[] lines;
    private Thread thread;
    User user = new User("test@gmail.com","123456");



    HashMap<String, AvlTree<Car>> map = new HashMap<String, AvlTree<Car>>();



    final int firstReadVolume = 400;
    int currentCount;
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


        map.put("audi",new AvlTree<Car>());
        map.put("mercedes-benz",new AvlTree<Car>());
        map.put("bmw",new AvlTree<Car>());
        map.put("kia",new AvlTree<Car>());
        map.put("mazda",new AvlTree<Car>());
        map.put("subaru",new AvlTree<Car>());
        map.put("toyota",new AvlTree<Car>());

        String jsonFileString = Utils.getJsonFromAssets(context,"shuffled_car_data.json");
        lines= jsonFileString.split("\\n");


        for (int i =0;i<lines.length && i<firstReadVolume;i++){
            Car car = myJsonReader.parseOneLine(lines[i]);
            car.setId(i);
            car.setUser(user);
            cars.add(car);

            // insert to corresponding avlTree
            if(map.get(car.brand)!=null){
                map.get(car.brand).insert(car);
            }
        }
        currentCount = firstReadVolume;

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
    public HashMap<String, AvlTree<Car>> getMap() {
        return map;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep((int) (Math.random()*15000));
                Car car = myJsonReader.parseOneLine(lines[currentCount]);
                car.setId(currentCount);
                car.setUser(user);
                cars.add(car);

                // insert to corresponding avlTree
                if(map.get(car.brand)!=null){
                    map.get(car.brand).insert(car);
                }
                currentCount++;
                System.out.println("Successfully read in one line from json file");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(){
        thread = new Thread(this, "simulateAddingNewCars");
        thread.start();
    }
}
