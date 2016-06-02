package com.manolosmobile.fuimultado.callbacks.abstractions;

public interface OnBillsUpdatedCallback {
    void onSuccess();
    void onError(String errorMessage);
}
