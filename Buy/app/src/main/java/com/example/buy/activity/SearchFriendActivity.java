package com.example.buy.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.R;
import com.example.buy.adapter.SearchAdapter;
import com.example.buy.entity.User;
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.sqlite.SQLiteDAOImpl;
import com.example.buy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Zice Yan
 */
public class SearchFriendActivity extends MyBaseActivity {
    EditText editText;
    SearchAdapter searchAdapter;
    private SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_fri);

        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(new ArrayList<User>(), new SearchAdapter.CallBack() {
            @Override
            /**
             * add friends id from sqlitedAO
             * Author: Zice Yan
             */
            public void onAddFri(int id) {
                // add friends
                sqLiteDAO.addFriend(id);
                ToastUtils.showShortToast(getApplicationContext(),"Friend added successfully");
                doFetchData();
            }
        });
        recyclerView.setAdapter(searchAdapter);

        editText = findViewById(R.id.et);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    // search friends
                    doFetchData();
                }
                return false;
            }
        });

    }

    /**
     *fetch data
     * @Author Zice Yan
     */
    private void doFetchData(){
        String str = editText.getText().toString().trim();
        if(!TextUtils.isEmpty(str)) {
            List<User> list = sqLiteDAO.searchFriends(str);
            if(list.isEmpty()) {
                ToastUtils.showShortToast(getApplicationContext(),"Search result is empty");
            }
            searchAdapter.setNewData(list);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
