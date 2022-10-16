package com.example.buy.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.buy.R;
import com.example.buy.entity.User;
import com.example.buy.sqlite.DAOService;

import java.util.List;


public class SearchAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

    public interface CallBack {
        void onAddFri(int id);
    }

    private CallBack mCallBack;

    public SearchAdapter(@Nullable List<User> data,CallBack callBack) {
        super(R.layout.fri_search_item, data);
        mCallBack = callBack;
    }

    @Override
    protected void convert(BaseViewHolder helper, final User item) {
        helper.setText(R.id.tv1,"name:"+item.getName())
                .setText(R.id.tv2,"email:"+item.getEmail());
        //If there are no friends of the person or if the search comes up with yourself, the Add Friend button is not displayed.
        helper.getView(R.id.bt).setVisibility(DAOService.getInstance().getUser().hasThisFriends(item.getId())
                || item.getId() == DAOService.getInstance().getUser().getId() ? View.GONE : View.VISIBLE);
        helper.getView(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make a friend
                mCallBack.onAddFri(item.getId());
            }
        });
    }
}
