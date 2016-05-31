package com.manolosmobile.fuimultado.callbacks;

import android.app.Activity;

import com.manolosmobile.fuimultado.ui.DialogHelper;

/**
 * Created by phellipe on 5/19/16.
 */
public class FinishActivityCallback implements OnDatabaseOperationFinishCallback {

    private Activity activity;

    public FinishActivityCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onOperationFinish(boolean success, String errorMessage) {
        if (success) {
            activity.finish();
        } else {
            DialogHelper.createWarningDialog(activity, errorMessage);
        }
    }
}
