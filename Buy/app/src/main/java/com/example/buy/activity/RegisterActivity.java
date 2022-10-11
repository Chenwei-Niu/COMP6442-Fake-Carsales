package com.example.buy.activity;
//Author: Zice Yan
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;

import com.example.buy.R;
import com.example.buy.entity.User;
import com.example.buy.utils.ToastUtils;
import com.example.buy.sqlite.DAOService;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends MyBaseActivity implements View.OnClickListener {
    private TextInputLayout user, pwd,name,phone;
    private RadioButton nanRb;

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

    private String getStr(TextInputLayout textInputLayout){
        if(null == textInputLayout || null == textInputLayout.getEditText()) {
            return "";
        }
        return textInputLayout.getEditText().getText().toString().trim();
    }

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
                } else if (TextUtils.isEmpty(nameStr) ||
                        TextUtils.isEmpty(phoneStr)) {
                    ToastUtils.showShortToast(getApplicationContext(), "Personal information cannot be blank!");
                }  else {
                    User user = new User();
                    user.setEmail(resAccount);
                    user.setPassword(resPassword);
                    user.setName(nameStr);
                    user.setPhone(phoneStr);
                    // gender
                    user.setSex(nanRb.isChecked()? "male" :"female");

                    // Level Is it a member or a regular user
                    String str = DAOService.getInstance().register(user);

                    if(TextUtils.isEmpty(str)) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        ToastUtils.showShortToast(getApplicationContext(), "Register Success");
                        finish();
                    } else {
                        ToastUtils.showShortToast(getApplicationContext(), str);
                    }
                }
                break;
        }
    }

}
