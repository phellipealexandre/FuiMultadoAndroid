package com.manolosmobile.fuimultado.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.manolosmobile.fuimultado.bills.BillsUpdater;
import com.manolosmobile.fuimultado.callbacks.abstractions.OnBillsUpdatedCallback;
import com.manolosmobile.fuimultado.database.DatabaseManager;
import com.manolosmobile.fuimultado.sharedpreferences.DateSharedPreferencePersister;
import com.manolosmobile.fuimultado.utils.InternetUtils;

public class UpdateBillsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        DatabaseManager.init(context);

        if (InternetUtils.isConnected(context) && DateSharedPreferencePersister.hasOneDayPassedFromLastSave(context)) {
            BillsUpdater.updateBillsFromAllCarsInDatabase(context, new OnBillsUpdatedCallback() {
                @Override
                public void onSuccess() {
                    DateSharedPreferencePersister.persistDateNow(context);
                }

                @Override
                public void onError(String errorMessage) {

                }
            });
        }
    }
}
