package com.manolosmobile.fuimultado.callbacks.abstractions;

public interface OnDatabaseOperationFinishCallback {
    void onFinish(boolean success, String errorMessage);
}
