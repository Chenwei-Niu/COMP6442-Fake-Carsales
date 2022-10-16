package com.example.buy.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.Nullable;

import com.example.buy.R;
import com.example.buy.entity.User;
import com.example.buy.sqlite.DAOService;
import com.example.buy.utils.ToastUtils;
import com.google.android.material.textfield.TextInputLayout;

public class EditMemberInfoActivity extends MyBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);

        final User user = DAOService.getInstance().getUser();

        final TextInputLayout psd = findViewById(R.id.psd);
        psd.getEditText().setText(user.getPassword());
        final TextInputLayout name = findViewById(R.id.name);
        name.getEditText().setText(user.getName());
        final TextInputLayout phone = findViewById(R.id.phone);
        phone.getEditText().setText(user.getPhone());

        final RadioButton nanRb = findViewById(R.id.btnMan);
        nanRb.setChecked(TextUtils.equals(user.getSex(),"male"));
        final RadioButton nvRb = findViewById(R.id.btnWoman);
        nvRb.setChecked(!TextUtils.equals(user.getSex(),"male"));

        findViewById(R.id.res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordStr = getStr(psd);
                String nameStr = getStr(name);
                String phoneStr = getStr(phone);

                user.setPassword(passwordStr);
                user.setName(nameStr);
                user.setPhone(phoneStr);
                // gender
                user.setSex(nanRb.isChecked()? "male" :"female");

                // update User personal Information
                DAOService.getInstance().updateUserInfo(user);
                ToastUtils.showShortToast(getApplicationContext(),"Personal information updated successfully");
                finish();
            }
        });
    }

    private String getStr(TextInputLayout textInputLayout){
        if(null == textInputLayout || null == textInputLayout.getEditText()) {
            return "";
        }
        return textInputLayout.getEditText().getText().toString().trim();
    }

}
