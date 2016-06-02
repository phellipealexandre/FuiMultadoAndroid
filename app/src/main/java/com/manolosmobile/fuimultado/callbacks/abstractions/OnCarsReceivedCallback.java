package com.manolosmobile.fuimultado.callbacks.abstractions;

import com.manolosmobile.fuimultado.models.Car;

import java.sql.SQLException;
import java.util.List;

public interface OnCarsReceivedCallback {
    void onFinish(List<Car> cars);
}
