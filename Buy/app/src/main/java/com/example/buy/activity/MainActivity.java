package com.example.buy.activity;
//Author: ZiceYan
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.buy.R;
import com.example.buy.utils.ToastUtils;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

public class MainActivity extends MyBaseActivity implements OnClickListener {

    FragmentManager fragmentManager;


    private ImageView groupIv;
    private ImageView sleepIv;
    private ImageView mineIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        groupIv = findViewById(R.id.group_tab_iv);
        sleepIv = findViewById(R.id.search_tab_iv);
        mineIv = findViewById(R.id.mine_tab_iv);

        findViewById(R.id.search_ll).setOnClickListener(this);
        findViewById(R.id.group_ll).setOnClickListener(this);
        findViewById(R.id.mine_ll).setOnClickListener(this);
        defaultClick();//Set default

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.group_ll:
                selectFragment(0);
                break;
            case R.id.search_ll:
                selectFragment(1);
                break;
            case R.id.mine_ll:
                selectFragment(2);
                break;
            default:
                break;
        }
    }


    private void selectFragment(int index) {
        sleepIv.setSelected(false);
        groupIv.setSelected(false);
        mineIv.setSelected(false);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (index) {
            case 0:
                groupIv.setSelected(true);

                break;
            case 1:

                sleepIv.setSelected(true);

                break;
            case 2:
                mineIv.setSelected(true);

                break;

            default:
                break;
        }
        fragmentTransaction.commit();
    }

// default access
    private void defaultClick() {
        selectFragment(0);
    }
}