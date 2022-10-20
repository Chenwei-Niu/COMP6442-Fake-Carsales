package com.example.buy.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buy.utils.Utils;

public class MyBaseActivity extends AppCompatActivity {

    @Override
    /*Set transparent status bar*/
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setNavigationBarStatusBarTranslucent(this);
    }
}
