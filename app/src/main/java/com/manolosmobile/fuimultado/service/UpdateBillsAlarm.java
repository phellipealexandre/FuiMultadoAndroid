package com.manolosmobile.fuimultado.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class UpdateBillsAlarm {

    private static final int ALARM_ID = 1;
    private static final long ALARM_REPEATING_TIME = 1000 * 60 * 60 * 8;

    public static void initAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, UpdateBillsReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), ALARM_REPEATING_TIME, pendingIntent);
    }
}
