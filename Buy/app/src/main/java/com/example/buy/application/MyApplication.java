package com.example.buy.application;


import android.app.Application;

import com.example.buy.entity.Market;
import com.example.buy.entity.User;
import com.example.buy.sqlite.DAOService;

import org.litepal.LitePal;


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

        Market.getMarket().setContext(getApplicationContext());
        Market.getMarket().firstRetrieveCarData();
        Market.getMarket().start();
        System.out.println(Market.getMarket().getCarArray().toString());

    }
}
