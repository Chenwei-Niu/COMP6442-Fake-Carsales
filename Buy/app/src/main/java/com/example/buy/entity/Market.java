package com.example.buy.entity;

import android.content.Context;

import com.example.buy.avltree.AvlTree;
import com.example.buy.parser.MyJsonReader;
import com.example.buy.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * This class contains the ArrayList and AVLTrees of cars read from json file
 * This class uses Singleton pattern to make sure that this project only have one instance
 *
 * This class also uses Iterator pattern to make sure that the most important cars ArrayList
 * can be only accessed by iterator by other classes.
 *
 * @feature visualize items, search, simulate upload items stream
 * @author Chenwei Niu
 */
public class Market<firstReadVolume> implements Runnable, ArrayListIterableCollection {
    private MyJsonReader myJsonReader = new MyJsonReader();
    private ArrayList<Car> cars = new ArrayList<>();
    private Context context;
    private String[] lines;
    private Thread thread;
    private User user = new User("test@gmail.com","123456");



    private HashMap<String, AvlTree<Car>> map = new HashMap<String, AvlTree<Car>>();



    final int firstReadVolume = 400;
    int currentCount;
    private static Market market = new Market();

    private Market () {
    }
    public static Market getMarket(){
        return market;
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

    public void addOneCar(Car car){
        car.setId(currentCount++);
        cars.add(car);
        // insert to corresponding avlTree
        if(map.get(car.brand)!=null){
            map.get(car.brand).insert(car);
        }
    }

    public void removeCar(Car car){
        cars.remove(car); //remove from the cars list
        // remove from corresponding avlTree
        if(map.get(car.brand)!=null){
            map.get(car.brand).remove(car);
        }
    }

    /**
     * return the Array ArrayListIterator
     * @return
     */
    @Override
    public ArrayListIterator createIterator() {
        return new CarsConcreteArrayListIterator();
    }

    // inner class
    private class CarsConcreteArrayListIterator implements ArrayListIterator {
        int index;

        @Override
        public boolean hasNext() {
            if(!cars.isEmpty() && index < cars.size()){
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if(this.hasNext()) {
                return cars.get(index++);
            }
            return null;
        }
    }
}
