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

    public static void buildAlertMessageNoGps(final Context ctx) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(ctx.getString(R.string.gps_desactive))
                .setCancelable(false)
                .setPositiveButton(ctx.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        ctx.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(ctx.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
