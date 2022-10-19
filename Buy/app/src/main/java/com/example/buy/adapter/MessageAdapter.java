package com.example.buy.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.buy.R;
import com.example.buy.entity.Message;
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.sqlite.SQLiteDAOImpl;

import java.util.List;


public class MessageAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {


    public MessageAdapter(@Nullable List<Message> data) {
        super(R.layout.fri_message_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Message item) {
        SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();
        TextView tvLeft = helper.getView(R.id.tvLeft);
        TextView tvRight = helper.getView(R.id.tvRight);
        tvLeft.setVisibility(View.GONE);
        tvRight.setVisibility(View.GONE);
        // If there are no friends of the person or if the search comes up with yourself, the Add Friend button is not displayed.
        // Determines whether the current message is displayed on the left side of the receiver or the right side of the sender
        int nowId = sqLiteDAO.getUser().getId();
        if (nowId == item.getSendUserId()) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(item.getContent());
        } else {
            tvLeft.setVisibility(View.VISIBLE);
            tvLeft.setText(item.getContent());
        }
    }
}
