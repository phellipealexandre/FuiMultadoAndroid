package com.manolosmobile.fuimultado.callbacks;

import com.manolosmobile.fuimultado.models.Car;

/**
 * Created by phellipe on 5/16/16.
 */
public class CallbackResult {

    private Car resultCar;
    private boolean hasError;
    private String errorMessage;

    public CallbackResult(Car resultCar) {
        this.resultCar = resultCar;
        this.hasError = false;
        this.errorMessage = "";
    }

    public CallbackResult(String errorMessage) {
        this.resultCar = null;
        this.hasError = true;
        this.errorMessage = errorMessage;
    }

    public Car getResultCar() {
        return resultCar;
    }

    public boolean hasError() {
        return hasError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
