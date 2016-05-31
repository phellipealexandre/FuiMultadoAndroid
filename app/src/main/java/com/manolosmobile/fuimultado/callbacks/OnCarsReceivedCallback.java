package com.manolosmobile.fuimultado.callbacks;

import com.manolosmobile.fuimultado.models.Car;

import java.sql.SQLException;
import java.util.List;

public interface OnCarsReceivedCallback {
    void onSuccess(List<Car> cars);
}
