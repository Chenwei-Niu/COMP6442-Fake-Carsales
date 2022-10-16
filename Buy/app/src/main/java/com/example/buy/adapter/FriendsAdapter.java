package com.example.buy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.buy.R;
import com.example.buy.entity.User;

import java.util.List;


public class FriendsAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    public FriendsAdapter(@Nullable List<User> data) {
        super(R.layout.fri_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        helper.setText(R.id.tv1,"name:"+item.getName())
                .setText(R.id.tv2,"email:"+item.getEmail());
    }
}
