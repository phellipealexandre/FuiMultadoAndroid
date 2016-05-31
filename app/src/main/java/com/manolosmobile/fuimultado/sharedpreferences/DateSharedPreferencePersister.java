package com.manolosmobile.fuimultado.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateSharedPreferencePersister {

    private final static String DATE_KEY = "datekey";
    private final static String DATE_FORMAT = "dd/MM/yyyy HH:mm";

    public static void persistDateNow(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(DATE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Date dateNow = new Date();
        DateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        editor.putString(DATE_KEY, format.format(dateNow));
        editor.apply();
    }

    public static boolean hasOneDayPassedFromLastSave(Context context) {
        boolean hasOneDayPassed = false;

        Date lastDate = getLastSavedDate(context);
        Calendar c = Calendar.getInstance();
        c.setTime(lastDate);
        c.add(Calendar.DATE, 1);

        Date lastDateWithOneDayMore = c.getTime();
        Date nowDate = new Date();

        if (lastDateWithOneDayMore.before(nowDate)) {
            hasOneDayPassed = true;
        }

        return hasOneDayPassed;
    }

    public static Date getLastSavedDate(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(DATE_KEY, Context.MODE_PRIVATE);
        DateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        String strDate = sharedPref.getString(DATE_KEY, format.format(new Date()));

        Date lastSavedDate = null;
        try {
            lastSavedDate = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return lastSavedDate;
    }

    public static String getLastSavedDateAsString(Context context) {
        Date lastDate = getLastSavedDate(context);
        DateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return format.format(lastDate);
    }
}
