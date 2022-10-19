package com.example.buy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.buy.R;
import com.example.buy.entity.User;
import com.example.buy.sqlite.DAOService;
import com.example.buy.utils.ToastUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

public class EditMemberInfoActivity extends MyBaseActivity {
    ImageView imageView;
    String picUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);

        final User user = DAOService.getInstance().getUser();

        // head
        // 如果保存过头像，直接使用数据库中保存的头像
        imageView = findViewById(R.id.iv_head);
        picUrl = user.getPicUrl();
        // 由内容的话 才进行加载
        if(!TextUtils.isEmpty(picUrl)) {
            Glide.with(this).load(picUrl).into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.ic_default_head);
        }

        // 点击拉起
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector
                        .create(EditMemberInfoActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(false);
            }
        });

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

                // 图片地址，如果设置过了，才进行赋值
                if(!TextUtils.isEmpty(picUrl)) {
                    user.setPicUrl(picUrl);
                }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);

                //使用 Glide 加载图片
                Glide.with(this)
                        .load(pictureBean.getUri())
                        .apply(RequestOptions.centerCropTransform()).into(imageView);
                picUrl = pictureBean.getUri().toString();
            }
        }
    }
}
