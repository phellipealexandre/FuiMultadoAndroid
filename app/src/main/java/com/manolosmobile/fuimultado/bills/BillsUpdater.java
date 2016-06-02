package com.manolosmobile.fuimultado.bills;

import android.content.Context;

import com.manolosmobile.fuimultado.callbacks.abstractions.OnCarsReceivedCallback;
import com.manolosmobile.fuimultado.callbacks.abstractions.OnBillsUpdatedCallback;
import com.manolosmobile.fuimultado.database.DatabaseManager;
import com.manolosmobile.fuimultado.callbacks.abstractions.OnDatabaseOperationFinishCallback;
import com.manolosmobile.fuimultado.exceptions.ParserNotFoundException;
import com.manolosmobile.fuimultado.models.Car;
import com.manolosmobile.fuimultado.notification.NotificationHelper;
import com.manolosmobile.fuimultado.ui.DialogHelper;
import com.manolosmobile.fuimultado.callbacks.DetranCallbackResult;
import com.manolosmobile.fuimultado.callbacks.abstractions.OnDetranParseFinishedCallback;
import com.manolosmobile.fuimultado.web.DetranParser;
import com.manolosmobile.fuimultado.web.DetranParserFactory;
import com.manolosmobile.fuimultado.web.EEstate;

import java.util.Collections;
import java.util.List;

public class BillsUpdater {

    private static List<Car> threadSafeList = null;

    public static synchronized void updateBillsFromAllCarsInDatabase(final Context context, final OnBillsUpdatedCallback callback) {
        if (threadSafeList == null) {
            DatabaseManager.getInstance().findAllCars(new OnCarsReceivedCallback() {
                @Override
                public void onFinish(List<Car> cars) {
                    threadSafeList = Collections.synchronizedList(cars);
                    for (final Car currentCar : cars) {
                        updateCarInfo(context, callback, currentCar);
                    }
                }
            });
        }
    }

    private static void updateCarInfo(final Context context, final OnBillsUpdatedCallback callback, final Car currentCar) {
        try {
            DetranParser parser = DetranParserFactory.createParser(context, EEstate.getEnumByString(currentCar.getEstate()));
            parser.getCarInfoFromWeb(currentCar.getPlate(), currentCar.getRenavam(), new OnDetranParseFinishedCallback() {
                @Override
                public void onFinish(final DetranCallbackResult result) {
                    if (result.hasError()) {
                        threadSafeList.remove(currentCar);
                        callback.onError(result.getErrorMessage());

                        if (threadSafeList.isEmpty()) {
                            threadSafeList = null;
                        }
                    } else {
                        NotificationHelper.notificateCarUpdate(context, currentCar, result.getResultCar());
                        updateCarOnDatabase(callback, currentCar, result.getResultCar());
                    }
                }
            });
        } catch (ParserNotFoundException e) {
            DialogHelper.createWarningDialog(context, e.getMessage());
        }
    }

    private static void updateCarOnDatabase(final OnBillsUpdatedCallback callback, final Car currentCar, final Car updatedCar) {
        DatabaseManager.getInstance().updateAllBills(updatedCar, new OnDatabaseOperationFinishCallback() {
            @Override
            public void onFinish(boolean success, String errorMessage) {
                threadSafeList.remove(currentCar);
                if (threadSafeList.isEmpty()) {
                    callback.onSuccess();
                    threadSafeList = null;
                }
            }
        });
    }
}
