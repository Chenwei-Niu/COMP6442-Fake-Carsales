package com.example.buy.utils;
//Author: Chenwei Niu

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 *  KeyBoard Utils
 * @feature search,upload new car
 * @author Chenwei Niu
 */
public class KeyBoardUtils {
    /**
     * Used to hide the soft keyboard
     * @param context
     */
    public static void hideKeyBoard(Activity context){
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        // hide the keyboard
        inputMethodManager.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(),0);
    }

}
