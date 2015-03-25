package com.suricapp.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.suricapp.views.R;

/**
 * Created by maxence on 19/03/15.
 */
public class CheckConnection {

    /**
     * Function to know if network is available
     * @param context
     * @return boolean
     */
    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo information = manager.getActiveNetworkInfo();
        return information != null && information.isConnectedOrConnecting();
    }


}
