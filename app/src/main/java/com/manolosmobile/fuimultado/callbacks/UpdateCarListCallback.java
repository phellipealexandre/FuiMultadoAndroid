package com.manolosmobile.fuimultado.callbacks;

import com.manolosmobile.fuimultado.MainActivity;
import com.manolosmobile.fuimultado.ui.DialogHelper;

public class UpdateCarListCallback implements OnDatabaseOperationFinishCallback {

    private MainActivity activity;

    public UpdateCarListCallback(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onOperationFinish(boolean success, String errorMessage) {
        if (success) {
            activity.updateList();
        } else {
            DialogHelper.createWarningDialog(activity, errorMessage);
        }
    }
}
