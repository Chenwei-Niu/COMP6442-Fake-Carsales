package com.example.buy.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public static Uri calanderURL = CalendarContract.Calendars.CONTENT_URI;
    public static Uri calanderEventURL = CalendarContract.Events.CONTENT_URI;;
    public static Uri calanderRemiderURL = CalendarContract.Reminders.CONTENT_URI;


    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static void setNavigationBarStatusBarTranslucent(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(option);
        activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    public static boolean isNotEmpty(Collection collection){
        return null != collection && !collection.isEmpty();
    }


    public static String getDateStr(Date date){
        return format.format(date);
    }

    public static String getDateAndTimeStr(Date date){
        return format2.format(date);
    }

    public static String getNowTimeStr(){
        return format.format(new Date());
    }

    public static String getNowDateAndTimeStr(){
        return format2.format(new Date());
    }


    public static String getTILStr(TextInputLayout textInputLayout){
        return textInputLayout.getEditText().getText().toString().trim();
    }

    /**
     * input events to log
     */
    public static void writeToCalendar(Context context, String title, String desc, String date) {
        //select Id
        String calId = "";
        Cursor userCursor = context.getContentResolver().query(calanderURL, null,
                null, null, null);
        if (userCursor.getCount() > 0) {
            userCursor.moveToFirst();
            calId = userCursor.getString(userCursor.getColumnIndex("_id"));

        }
        ContentValues event = new ContentValues();
        event.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        event.put("title", title);
        event.put("description", desc);
        event.put("calendar_id", calId);

        long start = 0;
        try {
            start = format2.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        event.put(CalendarContract.Events.DTSTART, start);
        event.put(CalendarContract.Events.DTEND, start+ 5 * 60 * 1000);
        event.put(CalendarContract.Events.HAS_ALARM, 1);
        event.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());


        Uri newEvent = context.getContentResolver().insert(calanderEventURL, event);
        long id = Long.parseLong(newEvent.getLastPathSegment());
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, id);
        values.put(CalendarContract.Reminders.MINUTES, 0);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        context.getContentResolver().insert(calanderRemiderURL, values);
        Log.e("xwf", "writeCalendarEvent Insert event successful!!!");
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
