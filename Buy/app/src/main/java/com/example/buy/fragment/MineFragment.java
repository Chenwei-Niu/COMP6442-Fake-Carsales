package com.example.buy.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.buy.R;
import com.example.buy.activity.EditMemberInfoActivity;
import com.example.buy.activity.UploadCarActivity;
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

/**
 * @author Chenwei Niu
 */
public class MineFragment extends Fragment implements ListenerFragment{
    private ListView myCarListView;
    private SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();
    private FragmentTransaction fragmentTransaction;
    private ArrayList<CarView> carViewArrayList = new ArrayList<>();
    private ImageView avatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_mine, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.edit_info);
        view.findViewById(R.id.edit_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Skip to change personal information page
                Intent intent = new Intent(getContext(), EditMemberInfoActivity.class);
                Objects.requireNonNull(getContext()).startActivity(intent);
            }
        });

        view.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        view.findViewById(R.id.upload_car).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Skip to change personal information page
                Intent intent = new Intent(getActivity(), UploadCarActivity.class);
                startActivityForResult(intent,12345);
            }
        });

        myCarListView = view.findViewById(R.id.myCar_listView);
        User user = sqLiteDAO.getUser();
        carViewArrayList.clear();
        System.out.println(user.getOnSaleCars());
        for (int i=0;i<user.getOnSaleCars().size();i++){

            CarView carView = new CarView( user.getOnSaleCars().get(i));
            carView.setLikeImage(R.drawable.trash_icon);

            carViewArrayList.add(carView);
        }

        // create the instance of the CarViewAdapter and pass the carArray into it
        CarViewAdapter carViewAdapter = new CarViewAdapter(Objects.requireNonNull(getActivity()),carViewArrayList,this);

        // get the instance of the listView in this activity, and set the Adapter for listview
        myCarListView.setAdapter(carViewAdapter);

        // load the user's avatar
        avatar = view.findViewById(R.id.mine_avatar);
        String picUrl = user.getPicUrl();
        // Load only if content
        if(!picUrl.isEmpty()) {
            Glide.with(this).load(picUrl).into(avatar);
        } else {
            avatar.setImageResource(R.mipmap.ic_default_head);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == 12345) && (resultCode== Activity.RESULT_OK)){
            refreshFragment();
        }
    }

    /**
     * Action when the trash icon is clicked
     * @param position
     * @author Chenwei Niu
     */
    @Override
    public void dealWithEvent(int position) {
        Car car = carViewArrayList.get(position).getCar();
        if(car.user==sqLiteDAO.getUser() && sqLiteDAO.getUser().getOnSaleCars().contains(car)){
            sqLiteDAO.getUser().getOnSaleCars().remove(car);
            Market.getMarket().removeCar(car);

        }
        // refresh the page
        refreshFragment();
    }

}