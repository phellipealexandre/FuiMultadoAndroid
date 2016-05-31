package com.manolosmobile.fuimultado.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnBootStartReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        UpdateBillsAlarm.initAlarm(context);
    }
}
