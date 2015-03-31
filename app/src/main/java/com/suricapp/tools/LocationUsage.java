package com.suricapp.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.suricapp.models.LocationBetween;
import com.suricapp.views.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by maxence on 25/03/15.
 */
public class LocationUsage {

    public static final double latitude_between = 0.008998;
    public static final double longitude_bewteen = 0.012984;

    /**
     * Ask to user to activate gps or not.
     * @param ctx
     */
    public static void buildAlertMessageNoGps(final Context ctx) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(ctx.getString(R.string.gps_desactive))
                .setCancelable(false)
                .setPositiveButton(ctx.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        ctx.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(ctx.getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     *
     * @param lat
     * @param longi
     * @return distance in meters
     */
    public static int disctanceBetween(double lat, double longi,Context ctx)
    {
        Location targetLocation = new Location("");//provider name is unecessary
        targetLocation.setLatitude(lat);//your coords of course
        targetLocation.setLongitude(longi);
        float distanceInMeters =  targetLocation.distanceTo(getLastKnownLocation(ctx));
        return (int)distanceInMeters;
    }

    /**
     * Get the best location
     * @return
     */
    public static Location getLastKnownLocation( final Context ctx) {
        LocationManager mLocationManager = (LocationManager)ctx.getApplicationContext().getSystemService(ctx.LOCATION_SERVICE);
        if ( !mLocationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            return null;
        }
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public static LocationBetween getLocationBetween(Location actual,Context ctx)
    {
        double rayonPref = new Integer(ctx.getSharedPreferences(Variables.SURICAPPREFERENCES,Context.MODE_PRIVATE).getInt("rayon",0)).doubleValue();
        double lat1 = Math.round((actual.getLatitude() - (rayonPref * latitude_between)) * 1000000.0) / 1000000.0;;
        double long1=Math.round( (actual.getLongitude() - (rayonPref*longitude_bewteen)) * 1000000.0 ) / 1000000.0;
        double lat2=Math.round( (actual.getLatitude() + (rayonPref*latitude_between)) * 1000000.0 ) / 1000000.0;
        double long2 =Math.round( (actual.getLongitude() +(rayonPref*longitude_bewteen)) * 1000000.0 ) / 1000000.0;
        LocationBetween lb = new LocationBetween(lat1,long1,lat2,long2);
        Log.w("POINT",lb.toString());
        return  lb;
    }


}
