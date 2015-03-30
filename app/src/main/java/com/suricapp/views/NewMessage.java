package com.suricapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.suricapp.models.Message;
import com.suricapp.models.User;
import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.tools.CheckConnection;
import com.suricapp.tools.DialogCreation;
import com.suricapp.tools.LocationUsage;
import com.suricapp.tools.Variables;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


public class NewMessage extends ActionBarActivity implements CompoundButton.OnCheckedChangeListener , View.OnClickListener{

    // Check box
    private CheckBox mTransportCheckBox;
    private CheckBox mPromotionCheckBox;
    private CheckBox mCovoiturageCheckBox;
    private CheckBox mEventCheckBox;
    private CheckBox mRencontreCheckBox;
    private CheckBox mAutoriteCheckBox;

    // Button
    private Button mEnvoyerButton;

    //Text view
    private EditText mTitreTextView;
    private EditText mMessageTextView;

    private double lat;
    private double longi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_message);
        super.onCreate(savedInstanceState);

        //View settings
        mTransportCheckBox = (CheckBox) findViewById(R.id.activity_new_message_categorie_transport);
        mTransportCheckBox.setOnCheckedChangeListener(this);
        mPromotionCheckBox = (CheckBox) findViewById(R.id.activity_new_message_categorie_promotion);
        mPromotionCheckBox.setOnCheckedChangeListener(this);
        mCovoiturageCheckBox= (CheckBox) findViewById(R.id.activity_new_message_categorie_covoiturage);
        mCovoiturageCheckBox.setOnCheckedChangeListener(this);
        mEventCheckBox= (CheckBox) findViewById(R.id.activity_new_message_categorie_evenement);
        mEventCheckBox.setOnCheckedChangeListener(this);
        mRencontreCheckBox= (CheckBox) findViewById(R.id.activity_new_message_categorie_rencontre);
        mRencontreCheckBox.setOnCheckedChangeListener(this);
        mAutoriteCheckBox= (CheckBox) findViewById(R.id.activity_new_message_categorie_autorites);
        mAutoriteCheckBox.setOnCheckedChangeListener(this);
        mEnvoyerButton = (Button) findViewById(R.id.activity_new_message_envoyer);
        mEnvoyerButton.setOnClickListener(this);
        mTitreTextView = (EditText) findViewById(R.id.activity_new_message_titre);
        // text change listener
        mTitreTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>= 60)
                    DialogCreation.createDialog(getLocalContext(),getString(R.string.titre_trop_long)
                            ,getString(R.string.titre_trop_long_desc));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mMessageTextView = (EditText) findViewById(R.id.activity_new_message_message);
        mMessageTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>= 150)
                    DialogCreation.createDialog(getLocalContext(),getString(R.string.message_trop_long)
                            ,getString(R.string.message_trop_long_desc));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_message, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            if (buttonView.getId() != R.id.activity_new_message_categorie_transport)
                mTransportCheckBox.setChecked(false);
            if (buttonView.getId() != R.id.activity_new_message_categorie_rencontre)
                mRencontreCheckBox.setChecked(false);
            if (buttonView.getId() != R.id.activity_new_message_categorie_evenement)
                mEventCheckBox.setChecked(false);
            if (buttonView.getId() != R.id.activity_new_message_categorie_promotion)
                mPromotionCheckBox.setChecked(false);
            if (buttonView.getId() != R.id.activity_new_message_categorie_autorites)
                mAutoriteCheckBox.setChecked(false);
            if (buttonView.getId() != R.id.activity_new_message_categorie_covoiturage)
                mCovoiturageCheckBox.setChecked(false);
        }
    }

    private boolean doesOneCategorieChecked()
    {
        return mTransportCheckBox.isChecked()
                || mRencontreCheckBox.isChecked()
                || mEventCheckBox.isChecked()
                || mPromotionCheckBox.isChecked()
                || mAutoriteCheckBox.isChecked()
                || mCovoiturageCheckBox.isChecked();
    }

    private int getIdCheck()
    {
        if(mTransportCheckBox.isChecked())
            return 2;
        if(mPromotionCheckBox.isChecked())
            return 3;
        if(mRencontreCheckBox.isChecked())
            return 4;
        if(mEventCheckBox.isChecked())
            return 5;
        if(mAutoriteCheckBox.isChecked())
            return 6;
        if(mCovoiturageCheckBox.isChecked())
            return 7;
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_new_message_envoyer:
                if(CheckConnection.isNetworkAvailable(getLocalContext())) {
                    if (mMessageTextView.getText().toString().trim().matches("") ||
                            mTitreTextView.getText().toString().trim().matches("")) {
                        DialogCreation.createDialog(getLocalContext(),
                                getString(R.string.champs_requis),getString(R.string.champs_requis_desc));

                    } else if (!doesOneCategorieChecked()) {
                        DialogCreation.createDialog(getLocalContext(),
                                getString(R.string.erreur), getString(R.string.no_category_choose));
                    } else {
                        Location location = LocationUsage.getLastKnownLocation(this);
                        if(location == null ) {
                            LocationUsage.buildAlertMessageNoGps(this);
                            break;
                        }
                        // Date of post message
                        Timestamp stamp = new Timestamp(System.currentTimeMillis());

                        // Message création
                        Message mess = new Message();
                        try {
                            mess.setMessage_title_fr_fr(URLEncoder.encode(mTitreTextView.getText().toString(), "UTF-8"));
                            mess.setMessage_content_fr_fr(URLEncoder.encode(mMessageTextView.getText().toString(),"UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            Log.w("EXEPTION",e.toString());
                        }
                        mess.setMessage_date(stamp);
                        SharedPreferences preferences = getSharedPreferences(Variables.SURICAPPREFERENCES,Context.MODE_PRIVATE);
                        mess.setMessage_id_user_fk(Integer.parseInt(preferences.getString("userLogId",null)));
                        mess.setMessage_id_category_fk(getIdCheck());
                        mess.setMessage_latitude(location.getLatitude());
                        mess.setMessage_longitude(location.getLongitude());
                        // Sent the message to server
                        HTTPAsyncTask task = new HTTPAsyncTask(this);
                        task.execute(null, Variables.POSTMESSAGE,"POST",mess.objectToNameValuePair());
                        task.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
                            @Override
                            public void setMyTaskComplete(String message){
                                Toast.makeText(getLocalContext(), getString(R.string.message_saved), Toast.LENGTH_LONG).show();
                                setResult(Variables.REQUESTLOADMESSAGE);
                                ((Activity) getLocalContext()).finish();
                            }
                        });
                    }
                }
                else
                {
                    DialogCreation.createDialog(getLocalContext(),getString(R.string.no_network_available)
                            ,getString(R.string.no_network_available_desc));
                }
                break;
        }
    }

    private Context getLocalContext()
    {
        return this;
    }
}
