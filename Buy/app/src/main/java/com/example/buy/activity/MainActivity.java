package com.example.buy.activity;
//Author: ZiceYan, Chenwei Niu Canxuan Gang

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.buy.R;
import com.example.buy.entity.Car;
import com.example.buy.fragment.BuyFragment;
import com.example.buy.entity.Market;
import com.example.buy.entity.State;
import com.example.buy.fragment.FollowFragment;
import com.example.buy.fragment.FriendFragment;
import com.example.buy.fragment.MineFragment;
import com.example.buy.entity.User;
import com.example.buy.fragment.SearchFragment;
import com.example.buy.sqlite.DAOService;
import com.example.buy.view.CarView;
import com.example.buy.view.CarViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends MyBaseActivity implements OnClickListener {

    FragmentManager fragmentManager;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    User user;

    private BuyFragment buyFragment;
    private SearchFragment searchFragment;
    private FriendFragment friendFragment;
    private MineFragment mineFragment;
    private FollowFragment followFragment;
    private ImageView groupIv;
    private ImageView searchIv;
    private ImageView friendIv;
    private ImageView mineIv;
    private ImageView followIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        groupIv = findViewById(R.id.group_tab_iv);
        searchIv = findViewById(R.id.search_tab_iv);
        friendIv = findViewById(R.id.friend_tab_iv);
        mineIv = findViewById(R.id.mine_tab_iv);
        followIv=findViewById(R.id.mine_follow_iv);
        findViewById(R.id.ll1).setOnClickListener(this);
        findViewById(R.id.ll2).setOnClickListener(this);
        findViewById(R.id.ll3).setOnClickListener(this);
        findViewById(R.id.ll4).setOnClickListener(this);
        findViewById(R.id.ll5).setOnClickListener(this);
        defaultClick();//set default

        user = DAOService.getInstance().getUser();
//        myRef.child("cars").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                System.out.println(String.valueOf(task.getResult().getValue()));
//            }
//        });



//        writeNewCar(0,"AMG GT",2019,30000,"V8",1000,"VIC","sedan"
//                ,"automatic","V8",State.ONSALE,"benz");
//        writeNewCar(1,"C260",2018,30000,"V4",1000,"VIC","sedan"
//                ,"automatic","V8",State.ONSALE,"benz");

//        myRef.child("cars").orderByChild("information").startAt("a");

//        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if(!task.isSuccessful()) {
//                    Log.e("firebase","Error getting data", task.getException());
//                } else {
//                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                }
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll1:
                selectFragment(0);
                break;
            case R.id.ll2:
                selectFragment(1);
                break;
            case R.id.ll3:
                selectFragment(2);
                break;
            case R.id.ll4:
                selectFragment(3);
                break;
            case R.id.ll5:
                selectFragment(4);
                break;
            default:
                break;
        }
    }


    private void selectFragment(int index) {
        friendIv.setSelected(false);
        searchIv.setSelected(false);
        groupIv.setSelected(false);
        mineIv.setSelected(false);
        followIv.setSelected(false);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (index) {
            case 0:
                if (buyFragment == null) {
                    buyFragment = new BuyFragment();
                }
                fragmentTransaction.replace(R.id.fl, buyFragment);
                groupIv.setSelected(true);

                break;
            case 1:
                if (searchFragment == null) {
                    searchFragment = new SearchFragment();
                }
                fragmentTransaction.replace(R.id.fl, searchFragment);
                searchIv.setSelected(true);

                break;
            case 2:
                if (friendFragment == null) {
                    friendFragment = new FriendFragment();
                }
                fragmentTransaction.replace(R.id.fl, friendFragment);
                friendIv.setSelected(true);

                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
                fragmentTransaction.replace(R.id.fl, mineFragment);
                mineIv.setSelected(true);

                break;
            case 4:
                if (followFragment == null) {
                    followFragment = new FollowFragment();
                }
                fragmentTransaction.replace(R.id.fl, followFragment);
                followIv.setSelected(true);

                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    //default enter
    private void defaultClick() {
        selectFragment(0);
    }

    public void writeNewCar(int id, String information,int year ,int price, String image, int odometer, String location, String bodyStyle, String transmission, String engine, State state, String brand){
        User user = (User) getIntent().getExtras().getSerializable("user");
        Car car = new Car(id,information, year,price, image, odometer, location, bodyStyle, transmission, engine, state, brand,user);
        myRef.child("cars").child(String.valueOf(id)).setValue(car);
    }
}