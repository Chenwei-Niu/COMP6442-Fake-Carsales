package com.example.buy.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.buy.R;
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.sqlite.SQLiteDAOImpl;
import com.example.buy.utils.ToastUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

    /**
     * Login
     **/
public class LoginActivity extends MyBaseActivity implements View.OnClickListener {
    private TextInputLayout nameTIL;
    private TextInputLayout passTIL;
    private SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameTIL = findViewById(R.id.name);
        passTIL = findViewById(R.id.pass);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.zhuce).setOnClickListener(this);
    }
    /**
     * Click listener
     * findViewById(R.id.login).setOnClickListener(this);
     * findViewById(R.id.zhuce).setOnClickListener(this);
     * The code above does the listener setup
     * @param v
     * @Author Zice Yan
     */
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
                boolean success = sqLiteDAO.login(usernameStr,
                        passwordStr);
                ToastUtils.showShortToast(LoginActivity.this, success ? "Login succeeded" : "Wrong account or password");
                if (success) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
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
