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
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.sqlite.SQLiteDAOImpl;
import com.example.buy.utils.ToastUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

/**
 * edit memeber information
 * @Author Zice Yan
 */
public class EditMemberInfoActivity extends MyBaseActivity {
    ImageView imageView;
    String picUrl;
    SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);

        final User user = sqLiteDAO.getUser();

        // head
        // If an avatar has been saved, use the avatar saved in the database directly
        imageView = findViewById(R.id.iv_head);
        picUrl = user.getPicUrl();
        // Load only if content
        if(!TextUtils.isEmpty(picUrl)) {
            Glide.with(this).load(picUrl).into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.ic_default_head);
        }

        // click to select
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

                // Image address, only assign if set
                if(!TextUtils.isEmpty(picUrl)) {
                    user.setPicUrl(picUrl);
                }

                // update User personal Information
                sqLiteDAO.updateUserInfo(user);
                ToastUtils.showShortToast(getApplicationContext(),"Personal information updated successfully");
                finish();
            }
        });
    }

    /**
     * Generic method of fetching values for code simplification
     * Author Zice Yan
     */
    private String getStr(TextInputLayout textInputLayout){
        if(null == textInputLayout || null == textInputLayout.getEditText()) {
            return "";
        }
        return textInputLayout.getEditText().getText().toString().trim();
    }

    /**
     * Used in conjunction with the picture selection process, when the user selects a picture or takes a picture, the picture data will be called back here
     * At this point, two operations are performed: the saving of the image address and the display of the image
     * @Author Zice Yan
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*Results callback*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);

                //Loading images using Glide
                Glide.with(this)
                        .load(pictureBean.getUri())
                        .apply(RequestOptions.centerCropTransform()).into(imageView);
                picUrl = pictureBean.getUri().toString();
            }
        }
    }
}
