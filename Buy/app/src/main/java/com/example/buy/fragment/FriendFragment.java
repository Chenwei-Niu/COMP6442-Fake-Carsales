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

    private FriendsAdapter botanyAdapter;
    private TextView emptyTv;
    private SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }
    /**
     * After the page has been created to process the data
     * @Author Zice Yan
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Whether to display the empty data prompt
        emptyTv = view.findViewById(R.id.empty_tv);

        final List<User> list = sqLiteDAO.searchFriends();
        if(list.isEmpty()) {
            emptyTv.setVisibility(View.VISIBLE);
        } else {
            emptyTv.setVisibility(View.GONE);
        }
        // Initialize the page
        botanyAdapter = new FriendsAdapter(list);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(botanyAdapter);

        //Set the item's click event to be listened to, so that it will jump to the page where the message was sent when clicked,
        //and bring the recipient's id to it
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

        // Jump to the Friends search screen
        view.findViewById(R.id.iv_add_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getContext(), SearchFriendActivity.class),100);
            }
        });
    }

    /**
     * You may be redirected to the friend search screen and then to add a friend,
     *  if you have added a friend, you will need to refresh your friends list
     *  when you return to this page
     * @Author Zice Yan
     * @param requestCode
     * @param resultCode
     * @param data
     */
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