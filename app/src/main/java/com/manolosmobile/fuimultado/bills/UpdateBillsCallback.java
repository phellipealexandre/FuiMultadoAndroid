package com.manolosmobile.fuimultado.bills;

import com.manolosmobile.fuimultado.models.Car;

/**
 * Created by phellipe on 5/10/16.
 */
public interface UpdateBillsCallback {
    void onSuccess();
    void onError(String errorMessage);
}
