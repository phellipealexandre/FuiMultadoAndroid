package com.manolosmobile.fuimultado.callbacks;

import com.manolosmobile.fuimultado.models.Car;

public class DetranCallbackResult {

    private Car resultCar;
    private boolean hasError;
    private String errorMessage;

    public DetranCallbackResult(Car resultCar) {
        this.resultCar = resultCar;
        this.hasError = false;
        this.errorMessage = "";
    }

    public DetranCallbackResult(String errorMessage) {
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
