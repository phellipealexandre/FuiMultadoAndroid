package com.manolosmobile.fuimultado.application;

import android.app.Application;
import android.util.Log;

import com.manolosmobile.fuimultado.database.DatabaseManager;
import com.manolosmobile.fuimultado.service.UpdateBillsAlarm;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.init(getApplicationContext());
        UpdateBillsAlarm.initAlarm(getApplicationContext());
    }
}
