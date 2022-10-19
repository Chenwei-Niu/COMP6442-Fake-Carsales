package com.example.buy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.buy.R;
import com.example.buy.activity.MessageActivity;
import com.example.buy.activity.SearchFriendActivity;
import com.example.buy.adapter.FriendsAdapter;
import com.example.buy.entity.User;
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.sqlite.SQLiteDAOImpl;

import java.util.List;

public class FriendFragment extends Fragment {

    FriendsAdapter botanyAdapter;
    TextView emptyTv;
    SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        emptyTv = view.findViewById(R.id.empty_tv);

        final List<User> list = sqLiteDAO.searchFriends();
        if(list.isEmpty()) {
            emptyTv.setVisibility(View.VISIBLE);
        } else {
            emptyTv.setVisibility(View.GONE);
        }
        botanyAdapter = new FriendsAdapter(list);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(botanyAdapter);


        botanyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                User health = botanyAdapter.getData().get(position);
                // Jump to the message conversation page
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("healthId",health.getId());
                startActivity(intent);
            }
        });


        view.findViewById(R.id.iv_add_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getContext(), SearchFriendActivity.class),100);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final List<User> list = sqLiteDAO.searchFriends();
        if(list.isEmpty()) {
            emptyTv.setVisibility(View.VISIBLE);
        } else {
            emptyTv.setVisibility(View.GONE);
        }
        botanyAdapter.setNewData(list);
    }
}