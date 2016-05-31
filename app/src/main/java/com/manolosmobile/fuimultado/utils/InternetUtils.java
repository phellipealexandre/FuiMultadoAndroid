package com.manolosmobile.fuimultado.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by phellipe on 5/9/16.
 */
public class InternetUtils {

    public static boolean isConnected(Context context) {
        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectionManager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
