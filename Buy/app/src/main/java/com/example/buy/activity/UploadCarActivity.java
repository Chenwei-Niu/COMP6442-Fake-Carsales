package com.example.buy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.buy.R;
import com.example.buy.entity.Car;
import com.example.buy.entity.Market;
import com.example.buy.entity.State;
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.sqlite.SQLiteDAOImpl;
import com.example.buy.utils.KeyBoardUtils;
import com.example.buy.utils.ToastUtils;
import com.example.buy.utils.Utils;

public class UploadCarActivity extends MyBaseActivity{
    private SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();
    private EditText carInformation;
    private EditText carImage;
    private EditText carEngine;
    private EditText carOdometer;
    private EditText carPrice;
    private Spinner carYear;
    private Spinner carBrand;
    private Spinner carBodyStyle;
    private Spinner carLocation;
    private Spinner carTransmission;
    private Button carSubmitButton;
    private View.OnFocusChangeListener hideKeyBoardListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_car);
        // get the Objects by R.id
        carInformation= findViewById(R.id.upload_car_information);
        carImage= findViewById(R.id.upload_car_image);
        carEngine= findViewById(R.id.upload_car_engine);
        carOdometer= findViewById(R.id.upload_car_odometer);
        carPrice = findViewById(R.id.upload_car_price);
        carYear= findViewById(R.id.upload_year_spinner);
        carBrand= findViewById(R.id.upload_brand_spinner);
        carBodyStyle= findViewById(R.id.upload_bodystyle_spinner);
        carLocation= findViewById(R.id.upload_location_spinner);
        carTransmission= findViewById(R.id.upload_transmission_spinner);
        carSubmitButton= findViewById(R.id.upload_car_submit_button);

        // set onClickListener to the submit button
        carSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        // set onClickListener to all Edit field, if lose focus, then hide the soft keyboard
        hideKeyBoardListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    KeyBoardUtils.hideKeyBoard(UploadCarActivity.this);
                }
            }
        };
        carInformation.setOnFocusChangeListener(hideKeyBoardListener);
        carImage.setOnFocusChangeListener(hideKeyBoardListener);
        carEngine.setOnFocusChangeListener(hideKeyBoardListener);
        carOdometer.setOnFocusChangeListener(hideKeyBoardListener);
        carPrice.setOnFocusChangeListener(hideKeyBoardListener);


    }

    private void submit(){
        // Check if each information is filled out
        if (carInformation.getText().toString().isEmpty()){
            carInformation.setError("Car information is required");
            carInformation.requestFocus();
            return;
        }
        if (carImage.getText().toString().isEmpty()){
            carImage.setError("Car Image is required");
            carInformation.requestFocus();
            return;
        }
        if (carOdometer.getText().toString().isEmpty()){
            carOdometer.setError("Car odometer number is required");
            carOdometer.requestFocus();
            return;
        }
        if (carEngine.getText().toString().isEmpty()){
            carEngine.setError("Car engine information is required");
            carEngine.requestFocus();
            return;
        }
        if (carPrice.getText().toString().isEmpty()){
            carPrice.setError("Car price is required");
            carPrice.requestFocus();
            return;
        }
        if (carYear.getSelectedItemPosition()==0){
            ToastUtils.showShortToast(this,"Car year is required");
            carYear.requestFocus();
            return;
        }
        if (carBrand.getSelectedItemPosition()==0){
            ToastUtils.showShortToast(this,"Car brand is required");
            carBrand.requestFocus();
            return;
        }
        if (carBodyStyle.getSelectedItemPosition()==0){
            ToastUtils.showShortToast(this,"Car body style is required");
            carBodyStyle.requestFocus();
            return;
        }
        if (carLocation.getSelectedItemPosition()==0){
            ToastUtils.showShortToast(this,"Car location is required");
            carLocation.requestFocus();
            return;
        }
        if (carTransmission.getSelectedItemPosition()==0){
            ToastUtils.showShortToast(this,"Car transmission is required");
            carTransmission.requestFocus();
            return;
        }
        // check whether odometer and price are numbers
        if (!Utils.isANumber(carOdometer.getText().toString())){
            carOdometer.setError("You should type in a number");
            carOdometer.requestFocus();
            return;
        }
        if (!Utils.isANumber(carPrice.getText().toString())){
            carPrice.setError("You should type in a number");
            carPrice.requestFocus();
            return;
        }
        // check whether the car image URL is valid
        if (!URLUtil.isValidUrl(carImage.getText().toString())){
            carImage.setError("You should type in a valid URL for your car image");
            carImage.requestFocus();
            return;
        }

        // define String variables to instantiate the Car Object
        String information = carInformation.getText().toString();
        String year = carYear.getSelectedItem().toString();
        String price = carPrice.getText().toString();
        String image = carImage.getText().toString();
        String odometer = carOdometer.getText().toString();
        String location = carLocation.getSelectedItem().toString();
        String bodyStyle = carBodyStyle.getSelectedItem().toString();
        String transmission = carTransmission.getSelectedItem().toString();
        String engine = carEngine.getText().toString();
        State state = State.ONSALE;
        String brand = carBrand.getSelectedItem().toString();

        Car car = new Car(information,year,price,image,odometer,location,bodyStyle,transmission,engine,state,brand);
        car.setUser(sqLiteDAO.getUser());
        sqLiteDAO.getUser().getOnSaleCars().add(car);
        System.out.println(sqLiteDAO.getUser().getOnSaleCars());
        Market.getMarket().addOneCar(car);

        setResult(Activity.RESULT_OK);
        finish();
    }


}
