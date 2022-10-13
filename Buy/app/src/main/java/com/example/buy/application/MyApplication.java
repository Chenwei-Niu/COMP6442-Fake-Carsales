package com.example.buy.application;
// author: Zice Yan
import android.app.Application;


import com.example.buy.entity.Market;
import com.example.buy.entity.User;
import com.example.buy.sqlite.DAOService;
import com.google.gson.stream.JsonReader;

import org.litepal.LitePal;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;


import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LitePal.initialize(this);
        LitePal.getDatabase();

        // Check if there is currently a User account, if so it has already been instantiated, otherwise pre-set the data
        if(!DAOService.getInstance().hasUser()) {
            new User("comp2100@anu.au","comp2100").save();
            new User("comp6442@anu.au ","comp6442").save();
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("car_data.json"), StandardCharsets.UTF_8));
            Market.getMarket().setCarBufferedReader(bufferedReader);
//            Market.getMarket().setInputStreamReader(new InputStreamReader(getAssets().open("car_data.json"), StandardCharsets.UTF_8));
//            JsonReader jsonReader = new JsonReader(new InputStreamReader(getAssets().open("car_data.json"), StandardCharsets.UTF_8));
//              Market.getMarket().setJsonReader(jsonReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Market.getMarket().getCarArray().toString());

    }
}
