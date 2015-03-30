package com.suricapp.tools;

import android.util.Log;

import java.sql.Timestamp;

/**
 * Created by maxence on 30/03/15.
 */
public class DateManipulation {

    public static String timespanToString(Timestamp time)
    {
        String first = time.toString();
        String spaceplit[] = first.split(" ");
        String dateSPlit[] = spaceplit[0].split("-");
        String heureSplit[] = spaceplit[1].split("\\.");
        return "Le : "+dateSPlit[2]+"/"+dateSPlit[1]+"/"+dateSPlit[0]+" à "+heureSplit[0];
    }
}
