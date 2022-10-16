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

import com.example.buy.R;
import com.example.buy.activity.EditMemberInfoActivity;

import java.util.Objects;


public class MineFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_mine, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.edit_info);
        view.findViewById(R.id.edit_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Skip to change personal information page
                Intent intent = new Intent(getContext(), EditMemberInfoActivity.class);
                Objects.requireNonNull(getContext()).startActivity(intent);
            }
        });

        view.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }
}