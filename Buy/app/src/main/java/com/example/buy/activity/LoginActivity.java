package com.example.buy.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.buy.R;
import com.example.buy.sqlite.DAOService;
import com.example.buy.utils.ToastUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class LoginActivity extends MyBaseActivity implements View.OnClickListener {
    private TextInputLayout nameTIL;
    private TextInputLayout passTIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameTIL = findViewById(R.id.name);
        passTIL = findViewById(R.id.pass);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.zhuce).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String usernameStr = Objects.requireNonNull(nameTIL.getEditText()).getText().toString().trim();
                String passwordStr = Objects.requireNonNull(passTIL.getEditText()).getText().toString().trim();

                if (TextUtils.isEmpty(usernameStr) || TextUtils.isEmpty(passwordStr)) {
                    ToastUtils.showShortToast(LoginActivity.this, "Account or password cannot be empty");
                    return;
                }
                boolean success = DAOService.getInstance().login(usernameStr,
                        passwordStr);
                ToastUtils.showShortToast(LoginActivity.this, success ? "Login succeeded" : "Wrong account or password");
                if (success) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

                break;
            case R.id.zhuce:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
