package com.manolosmobile.fuimultado.callbacks;

import android.app.Activity;

import com.manolosmobile.fuimultado.callbacks.abstractions.OnDatabaseOperationFinishCallback;
import com.manolosmobile.fuimultado.ui.DialogHelper;

public class FinishActivityCallback implements OnDatabaseOperationFinishCallback {

    private Activity activity;

    public FinishActivityCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onFinish(boolean success, String errorMessage) {
        if (success) {
            activity.finish();
        } else {
            DialogHelper.createWarningDialog(activity, errorMessage);
        }
    }
}
