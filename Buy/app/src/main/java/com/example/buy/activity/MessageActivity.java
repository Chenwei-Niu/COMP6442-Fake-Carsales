package com.example.buy.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.R;
import com.example.buy.adapter.MessageAdapter;
import com.example.buy.entity.Message;
import com.example.buy.entity.User;
import com.example.buy.sqlite.SQLiteDAO;
import com.example.buy.sqlite.SQLiteDAOImpl;
import com.example.buy.utils.Utils;

import org.litepal.LitePal;

import java.util.List;

public class MessageActivity extends MyBaseActivity {
    EditText editText;
    MessageAdapter messageAdapter;
    private SQLiteDAO sqLiteDAO = SQLiteDAOImpl.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        final int id = getIntent().getIntExtra("healthId",-1);

        User user = LitePal.find(User.class,id);
        TextView tvName = findViewById(R.id.tvName);
        tvName.setText(user.getName());


        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<Message> messageList = sqLiteDAO.searchAllMessage(id);
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);

        editText = findViewById(R.id.et);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    String str = editText.getText().toString().trim();
                    if(TextUtils.isEmpty(str)) {
                        return false;
                    }
                    // To send a message Create a new message object
                    Message message = new Message();
                    message.setTime(System.currentTimeMillis());
                    message.setSendUserId(sqLiteDAO.getUser().getId());
                    message.setReceiveUserId(id);
                    message.setContent(str);
                    message.save();
                    // Data to be added to the list
                    messageList.add(message);
                    messageAdapter.setNewData(messageList);

                    // Data cleared after sending
                    editText.setText("");
                    Utils.hideSoftKeyboard(MessageActivity.this);
                }
                return false;
            }
        });

    }
}
