package com.manolosmobile.fuimultado.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.manolosmobile.fuimultado.utils.InternetUtils;

public class OnConnectedToInternetReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        UpdateBillsAlarm.initAlarm(context);
    }
}
