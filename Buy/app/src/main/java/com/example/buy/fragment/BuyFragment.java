package com.example.buy.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.buy.R;
import com.example.buy.entity.Car;
import com.example.buy.entity.ArrayListIterator;
import com.example.buy.entity.Market;
import com.example.buy.entity.User;
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.sqlite.SQLiteDAOImpl;
import com.example.buy.utils.ToastUtils;
import com.example.buy.view.CarView;
import com.example.buy.view.CarViewAdapter;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @feature Visualize cars
 * @author Chenwei Niu
 */
public class BuyFragment extends Fragment implements ListenerFragment{

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

        // get the Market Cars List through ArrayListIterator
        ArrayList<Car> carSourceList = new ArrayList<>();
        for (ArrayListIterator arrayListIterator = Market.getMarket().createIterator(); arrayListIterator.hasNext();){
            Car car = (Car) arrayListIterator.next();
            carSourceList.add(car);
        }

        if (carSourceList != null){
            System.out.println(carSourceList);
            for (int i=0;i<carSourceList.size();i++){

                CarView carView = new CarView( carSourceList.get(i));
                if ( carSourceList.get(i).favoriteUsers.contains(user)) {
                    carView.setLikeImage(R.drawable.red_heart);
                } else {
                    carView.setLikeImage(R.drawable.black_hollow_heart);
                }
                carViewArrayList.add(carView);
            }

            // create the instance of the CarViewAdapter and pass the carArray into it
            CarViewAdapter carViewAdapter = new CarViewAdapter(Objects.requireNonNull(getActivity()),carViewArrayList,this);

            // get the instance of the listView in this activity, and set the Adapter for listview
            listView.setAdapter(carViewAdapter);

        }
    }
    /**
     * refresh the current fragment
     * @author Chenwei Niu
     */
    public void refreshFragment(){
        fragmentTransaction = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT>=26){
            fragmentTransaction.setReorderingAllowed(false);
        }
        fragmentTransaction.detach(this).attach(this).commit();
    }

    /**
     * Action when the heart icon is clicked
     * @param position
     * @author Chenwei Niu
     */
    @Override
    public void dealWithEvent(int position) {
        User user = sqLiteDAO.getUser();
        Car car = carViewArrayList.get(position).getCar();
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
}