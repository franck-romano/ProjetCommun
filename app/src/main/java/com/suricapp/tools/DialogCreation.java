package com.suricapp.tools;

/**
 * Created by maxence on 19/03/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.suricapp.views.R;

/**
 * Do a simply alert dialog with title and message
 * @author Max
 *
 */
public abstract class DialogCreation {

    public DialogCreation(Context mContext, String title, String message) {
    }


    // Static method to use it every where
    public static void createDialog(Context mContext, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title).setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Create a alert dialog which show two buttons, positive who dismiss
     * alert dialog, and negative which end context activity
     *
     * @param mContext context where display alert dialog
     * @param title    Title
     * @param message
     */
    public static void createDialogYesNo(final Context mContext, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title).setMessage(message);
        builder.setPositiveButton(mContext.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(mContext.getString(R.string.no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Activity act = (Activity) mContext;
                act.finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Make a alert dialogue with two buttons, one positive and one negative,
     * if positive button is clicked, user will be redirect to Url, which is in parameter.
     *
     * @param mContext       the context who call the alert dialog
     * @param title          alertDialog title
     * @param message        alertDialog Message
     * @param positiveButton label for positive button
     * @param negativeButton label for negative button
     * @param Url            url if poisitvie  buttun is cliked
     */
    public static void createDialogForUrl(final Context mContext, String title, String message, String positiveButton, String negativeButton, final String Url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title).setMessage(message);
        builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
                mContext.startActivity(browserIntent);

            }
        });
        builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        AlertDialog dialogSeguin = builder.create();
        dialogSeguin.show();
    }
}