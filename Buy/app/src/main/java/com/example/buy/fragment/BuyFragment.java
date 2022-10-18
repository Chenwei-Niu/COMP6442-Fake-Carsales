package com.example.buy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.buy.R;
import com.example.buy.entity.Car;
import com.example.buy.entity.Market;
import com.example.buy.view.CarView;
import com.example.buy.view.CarViewAdapter;

import java.util.ArrayList;
import java.util.Objects;


public class BuyFragment extends Fragment {

    CarViewAdapter carViewAdapter;
    ListView listView;
    ArrayList<CarView> carViewArrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.main_listView);
        if (Market.getMarket().getCarArray() != null){
            System.out.println(Market.getMarket().getCarArray());
            for (int i=0;i<Market.getMarket().getCarArray().size();i++){

                CarView carView = new CarView((Car) Market.getMarket().getCarArray().get(i));
                carViewArrayList.add(carView);
            }

            // create the instance of the CarViewAdapter and pass the carArray into it
            CarViewAdapter carViewAdapter = new CarViewAdapter(Objects.requireNonNull(getActivity()),carViewArrayList);

            // get the instance of the listView in this activity, and set the Adapter for listview
            listView.setAdapter(carViewAdapter);
        }
    }
}