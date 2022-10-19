package com.example.buy.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.buy.R;
import com.example.buy.entity.Car;
import com.example.buy.entity.Market;
import com.example.buy.entity.User;
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.sqlite.SQLiteDAOImpl;
import com.example.buy.utils.ToastUtils;
import com.example.buy.view.CarView;
import com.example.buy.view.CarViewAdapter;

import java.util.ArrayList;
import java.util.Objects;


public class BuyFragment extends Fragment {

    private CarViewAdapter carViewAdapter;
    private FragmentTransaction fragmentTransaction;
    private ListView listView;
    private ArrayList<CarView> carViewArrayList;
    private SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carViewArrayList = new ArrayList<>();
        listView = view.findViewById(R.id.main_listView);
        User user = sqLiteDAO.getUser();
        if (Market.getMarket().getCarArray() != null){
            System.out.println(Market.getMarket().getCarArray());
            for (int i=0;i<Market.getMarket().getCarArray().size();i++){

                CarView carView = new CarView((Car) Market.getMarket().getCarArray().get(i));
                if ( ((Car) Market.getMarket().getCarArray().get(i)).favoriteUsers.contains(user)) {
                    carView.setLikeImage(R.drawable.red_heart);
                } else {
                    carView.setLikeImage(R.drawable.black_hollow_heart);
                }
                carViewArrayList.add(carView);
            }

            // create the instance of the CarViewAdapter and pass the carArray into it
            CarViewAdapter carViewAdapter = new CarViewAdapter(Objects.requireNonNull(getActivity()),carViewArrayList);

            // get the instance of the listView in this activity, and set the Adapter for listview
            listView.setAdapter(carViewAdapter);

            // setOnClickListener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Car car = carViewAdapter.getItem(position).getCar();
                    if(!car.favoriteUsers.contains(user) && !user.getFavoriteCars().contains(car)){

                        // check whether this car is uploaded by the current user
                        // User are only allowed to follow cars uploaded by others
                        if (user.getOnSaleCars().contains(car)){
                            ToastUtils.showShortToast(getContext(),"You are not allow to follow your on sale car");
                            return;
                        }
                        car.favoriteUsers.add(user);
                        user.getFavoriteCars().add(car);

                    } else {
                        car.favoriteUsers.remove(user);
                        user.getFavoriteCars().remove(car);
                    }


                    // Jump to the message conversation page
                    refreshFragment();
                }

            });


        }
    }

    public void refreshFragment(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT>=26){
            fragmentTransaction.setReorderingAllowed(false);
        }
        fragmentTransaction.detach(this).attach(this).commit();
    }
}