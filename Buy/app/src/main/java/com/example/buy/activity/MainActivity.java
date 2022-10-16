package com.example.buy.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.buy.R;
import com.example.buy.fragment.BuyFragment;
import com.example.buy.fragment.FriendFragment;
import com.example.buy.fragment.MineFragment;
import com.example.buy.fragment.SearchFragment;

public class MainActivity extends MyBaseActivity implements OnClickListener {

    FragmentManager fragmentManager;

    private BuyFragment buyFragment;
    private SearchFragment searchFragment;
    private FriendFragment friendFragment;
    private MineFragment mineFragment;

    private ImageView groupIv;
    private ImageView searchIv;
    private ImageView friendIv;
    private ImageView mineIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        groupIv = findViewById(R.id.group_tab_iv);
        searchIv = findViewById(R.id.search_tab_iv);
        friendIv = findViewById(R.id.friend_tab_iv);
        mineIv = findViewById(R.id.mine_tab_iv);

        findViewById(R.id.ll1).setOnClickListener(this);
        findViewById(R.id.ll2).setOnClickListener(this);
        findViewById(R.id.ll3).setOnClickListener(this);
        findViewById(R.id.ll4).setOnClickListener(this);
        defaultClick();//set default

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