package com.example.buy.fragment;
//import package com.example.buy.activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.buy.R;
import com.example.buy.entity.Car;
import com.example.buy.entity.Market;
import com.example.buy.entity.User;
import com.example.buy.sqlite.DAOService;
import com.example.buy.view.CarView;
import com.example.buy.view.CarViewAdapter;

import java.util.ArrayList;
import java.util.Objects;
//Author Canxuan Gang and Chenwei Niu
public class FollowFragment extends Fragment {
    ListView listView;
    ArrayList<CarView> carViewArrayList = new ArrayList<>();
    FragmentTransaction fragmentTransaction;
    TextView noFollowBanner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_follow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carViewArrayList = new ArrayList<>();
        listView = view.findViewById(R.id.follow_listView);
        User user = DAOService.getInstance().getUser();
        noFollowBanner=view.findViewById(R.id.no_follow_cars_banner);
        if(user.getFavoriteCars() != null && user.getFavoriteCars().isEmpty()) {
            noFollowBanner.setVisibility(View.VISIBLE);
        } else {
            noFollowBanner.setVisibility(View.GONE);
        }

        if (user.getFavoriteCars() != null){

            for (int i=0;i<user.getFavoriteCars().size();i++){

                CarView carView = new CarView( user.getFavoriteCars().get(i));
                carView.setLikeImage(R.drawable.red_heart);

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
                    car.favoriteUsers.remove(user);
                    user.getFavoriteCars().remove(car);


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
