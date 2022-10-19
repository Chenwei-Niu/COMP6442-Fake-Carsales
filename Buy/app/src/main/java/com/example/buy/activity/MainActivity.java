package com.example.buy.activity;
//Author: ZiceYan, Chenwei Niu

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.buy.R;
import com.example.buy.entity.Car;
import com.example.buy.fragment.BuyFragment;
import com.example.buy.entity.State;
import com.example.buy.fragment.FollowFragment;
import com.example.buy.fragment.FriendFragment;
import com.example.buy.fragment.MineFragment;
import com.example.buy.entity.User;
import com.example.buy.fragment.SearchFragment;
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.sqlite.SQLiteDAOImpl;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends MyBaseActivity implements OnClickListener {

    FragmentManager fragmentManager;
    User user;
    private SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();

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
        followIv = findViewById(R.id.follow_tab_iv);

        findViewById(R.id.ll1).setOnClickListener(this);
        findViewById(R.id.ll2).setOnClickListener(this);
        findViewById(R.id.ll3).setOnClickListener(this);
        findViewById(R.id.ll4).setOnClickListener(this);
        findViewById(R.id.ll5).setOnClickListener(this);
        defaultClick();//set default

        user = sqLiteDAO.getUser();
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

}