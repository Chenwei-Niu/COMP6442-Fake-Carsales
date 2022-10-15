package com.example.buy.activity;
//Author: ZiceYan, Chenwei Niu

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.buy.R;
import com.example.buy.entity.Car;
import com.example.buy.entity.Market;
import com.example.buy.entity.State;
import com.example.buy.entity.User;
import com.example.buy.view.CarView;
import com.example.buy.view.CarViewAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends MyBaseActivity implements OnClickListener {

    FragmentManager fragmentManager;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    User user;
    ListView listView;
    ArrayList<CarView> carViewArrayList = new ArrayList<>();
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

//        First time retrieve the car data from the json
        user = (User) getIntent().getExtras().getSerializable("USER");


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
        listView = findViewById(R.id.main_listView);
        if (Market.getMarket().getCarArray() != null){
            System.out.println(Market.getMarket().getCarArray());
            for (int i=0;i<Market.getMarket().getCarArray().size();i++){
                System.out.println(Market.getMarket().getCarArray().get(i));
                CarView carView = new CarView((Car) Market.getMarket().getCarArray().get(i));
                carViewArrayList.add(carView);
            }

            // create the instance of the CarViewAdapter and pass the carArray into it
            CarViewAdapter carViewAdapter = new CarViewAdapter(this,carViewArrayList);

            // get the instance of the listView in this activity, and set the Adapter for listview
            listView.setAdapter(carViewAdapter);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.group_ll:
                selectFragment(0);
                break;
            case R.id.search_ll:
                selectFragment(1);
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
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

    public void writeNewCar(int id, String information,int year ,int price, String image, int odometer, String location, String bodyStyle, String transmission, String engine, State state, String brand){
        User user = (User) getIntent().getExtras().getSerializable("user");
        Car car = new Car(id,information, year,price, image, odometer, location, bodyStyle, transmission, engine, state, brand,user);
        myRef.child("cars").child(String.valueOf(id)).setValue(car);
    }
}