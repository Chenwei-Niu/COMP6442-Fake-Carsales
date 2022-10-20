package com.example.buy.fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.buy.R;
import com.example.buy.entity.Car;
import com.example.buy.entity.User;
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.sqlite.SQLiteDAOImpl;
import com.example.buy.view.CarView;
import com.example.buy.view.CarViewAdapter;

import java.util.ArrayList;
import java.util.Objects;
/**
 * @feature Visualize following cars
 * @author Canxuan Gang, Chenwei Niu
 */
public class FollowFragment extends Fragment implements ListenerFragment{
    private ListView listView;
    private ArrayList<CarView> carViewArrayList = new ArrayList<>();
    private FragmentTransaction fragmentTransaction;
    private TextView noFollowBanner;
    private SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();
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
        User user = sqLiteDAO.getUser();
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
        car.favoriteUsers.remove(user);
        user.getFavoriteCars().remove(car);

        refreshFragment();
    }
}
