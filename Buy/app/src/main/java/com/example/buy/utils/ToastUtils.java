package com.example.buy.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static void showShortToast(Context context, String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
