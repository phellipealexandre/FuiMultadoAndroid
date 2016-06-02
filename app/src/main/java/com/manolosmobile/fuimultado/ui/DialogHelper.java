package com.manolosmobile.fuimultado.ui;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.manolosmobile.fuimultado.R;

import java.util.List;

public class DialogHelper {

    private static MaterialDialog currentDialog = null;

    public static void createWarningDialog(Context context, String message) {
        if (currentDialog == null) {
            currentDialog = new MaterialDialog.Builder(context)
                    .title(R.string.warning)
                    .content(message)
                    .positiveText(R.string.ok)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            currentDialog = null;
                        }
                    })
                    .show();
        }
    }

    public static void createAddCarDialog(Context context, List<String> availableParsers,
                                          MaterialDialog.SingleButtonCallback callback) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.add_car)
                .customView(R.layout.add_car_dialog, true)
                .positiveText(R.string.add)
                .neutralText(R.string.cancel)
                .onPositive(callback).build();

        Spinner spinner = (Spinner) dialog.findViewById(R.id.spinnerEstates);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>
                (context, android.R.layout.simple_spinner_dropdown_item, availableParsers);
        spinner.setAdapter(dataAdapter);

        dialog.show();
    }

    public static void createRemoveCarDialog(Context context, MaterialDialog.SingleButtonCallback callback) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.remove_car)
                .content(R.string.remove_car_content)
                .positiveText(R.string.remove)
                .neutralText(R.string.cancel)
                .onPositive(callback).build();

        dialog.show();
    }

    public static void createProgressDialog(Context context) {
        if (currentDialog == null) {
            currentDialog = new MaterialDialog.Builder(context)
                    .title(R.string.loading)
                    .content(R.string.wait)
                    .progress(true, 0)
                    .show();
        }
    }

    public static void dismissCurrentDialog() {
        if (currentDialog != null) {
            currentDialog.dismiss();
            currentDialog = null;
        }
    }
}
