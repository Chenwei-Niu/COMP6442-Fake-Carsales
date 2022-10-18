package com.example.buy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.buy.R;
import com.example.buy.entity.Car;
import com.example.buy.entity.Market;
import com.example.buy.view.CarView;
import com.example.buy.view.CarViewAdapter;

import java.util.ArrayList;


public class BuyFragment extends Fragment {

    ArrayList<CarView> carViewArrayList;
    CarViewAdapter carViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}