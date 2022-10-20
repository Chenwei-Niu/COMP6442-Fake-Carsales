package com.example.buy.application;


import android.app.Application;

import com.example.buy.entity.Market;
import com.example.buy.entity.User;
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.sqlite.SQLiteDAOImpl;

import org.litepal.LitePal;

/**
 * my Application
 * @Author Zice Yan
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();
        super.onCreate();
        // Third-party libraries are used and the sqlite database needs to be initialized here
        LitePal.initialize(this);
        LitePal.getDatabase();

        // Check if there is currently a User account, if so it has already been instantiated, otherwise pre-set the data
        if(!sqLiteDAO.hasUser()) {
            new User("comp2100@anu.au","comp2100").save();
            new User("comp6442@anu.au ","comp6442").save();
        }

        Market.getMarket().setContext(getApplicationContext());
        Market.getMarket().firstRetrieveCarData();
        Market.getMarket().start(); // start the thread of read one line after several seconds

    }
}
