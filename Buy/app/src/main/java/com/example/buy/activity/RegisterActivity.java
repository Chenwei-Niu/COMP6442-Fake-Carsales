package com.example.buy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.RadioButton;

import com.example.buy.R;
import com.example.buy.entity.User;
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.utils.ToastUtils;
import com.example.buy.sqlite.SQLiteDAOImpl;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RegisterActivity extends MyBaseActivity implements View.OnClickListener {
    private TextInputLayout user, pwd,name,phone;
    private RadioButton nanRb;
    private SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = findViewById(R.id.user);
        pwd = findViewById(R.id.psd);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);

        nanRb = findViewById(R.id.btnMan);
        findViewById(R.id.res).setOnClickListener(this);
    }

    /**
     * Generic get method
     * @Author: Zice Yan
     */
    private String getStr(TextInputLayout textInputLayout){
        if(null == textInputLayout || null == textInputLayout.getEditText()) {
            return "";
        }
        return textInputLayout.getEditText().getText().toString().trim();
    }
    /**
     * Click listener
     * The registration done here is to get the content entered on the page and store it in the database
     * If the registration is successful, the current registration page will be closed and the page will be redirected to the home page.
     * If it fails, it stays on the current page
     * @Author Zice Yan
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.res:
                String resAccount = getStr(user);
                String resPassword = getStr(pwd);
                String nameStr = getStr(name);
                String phoneStr = getStr(phone);
                if (TextUtils.isEmpty(resAccount) || TextUtils.isEmpty(resPassword)) {
                    ToastUtils.showShortToast(getApplicationContext(), "User name/password cannot be empty!");
                    break;
                }
                if (TextUtils.isEmpty(nameStr) ||
                        TextUtils.isEmpty(phoneStr)) {
                    ToastUtils.showShortToast(getApplicationContext(), "Personal information cannot be blank!");
                    break;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(resAccount).matches()){
                    ToastUtils.showShortToast(getApplicationContext(), "Please type in the correct Email");
                    break;
                }
                if (resPassword.length()<6){
                    ToastUtils.showShortToast(getApplicationContext(), "Password should be at least 6 characters");
                    break;
                }
                if (phoneStr.length()<10){
                    ToastUtils.showShortToast(getApplicationContext(), "Valid Australian phone number\n should be at least 10 digits");
                    break;
                }
                User user = new User();
                user.setEmail(resAccount);
                user.setPassword(resPassword);
                user.setName(nameStr);
                user.setPhone(phoneStr);
                // gender
                user.setSex(nanRb.isChecked()? "male" :"female");

                // Level Is it a member or a regular user
                String str = sqLiteDAO.register(user);

                if(TextUtils.isEmpty(str)) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    ToastUtils.showShortToast(getApplicationContext(), "Register Success");
                    finish();
                } else {
                    ToastUtils.showShortToast(getApplicationContext(), str);
                }

                break;
        }
    }

}
