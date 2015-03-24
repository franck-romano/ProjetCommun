package com.suricapp.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.suricapp.models.User;
import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.tools.Variables;


public class Inscription_2Activity extends ActionBarActivity implements View.OnClickListener{

    private HTTPAsyncTask task;

    // View element
    private CheckBox mTransportCheckBox;
    private CheckBox mPromotionCheckBox;
    private CheckBox mCovoiturageCheckBox;
    private CheckBox mEventCheckBox;
    private CheckBox mRencontreCheckBox;
    private CheckBox mAutoriteCheckBox;

    private Button mTerminerButton;
    // Progress bar
    private int mProgress = 0;

    // User from previous intent
    private User mUserToSent;

    private String photoFromLastIntent;

    public String getPhotoFromLastIntent() {
        return photoFromLastIntent;
    }

    public void setPhotoFromLastIntent(String photoFromLastIntent) {
        this.photoFromLastIntent = photoFromLastIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_2);

        SeekBar rayon = (SeekBar)findViewById(R.id.activity_inscription_2rayon);
        final TextView rayon_numeric = (TextView)findViewById(R.id.rayon_texte);

        mUserToSent = (User) getIntent().getSerializableExtra("user");

        //View Init
        mTransportCheckBox = (CheckBox) findViewById(R.id.activity_inscription_2_transport);
        mPromotionCheckBox = (CheckBox) findViewById(R.id.activity_inscription_2_promotion);
        mCovoiturageCheckBox = (CheckBox) findViewById(R.id.activity_inscription_2_covoiturage);
        mEventCheckBox = (CheckBox) findViewById(R.id.activity_inscription_2_evenement);
        mRencontreCheckBox = (CheckBox) findViewById(R.id.activity_inscription_2_rencontre);
        mAutoriteCheckBox = (CheckBox) findViewById(R.id.activity_inscription_2_autorites);
        mTerminerButton = (Button) findViewById(R.id.activity_inscription_2_terminer);
        mTerminerButton.setOnClickListener(this);

        // Initialiser le textView avec la valeur de départ.
        rayon_numeric.setText(rayon.getProgress() + " kms");
        mProgress = rayon.getProgress();

        rayon.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                mProgress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                rayon_numeric.setText(mProgress + " kms");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                rayon_numeric.setText(mProgress + " kms");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_inscription_2_terminer:
                task = new HTTPAsyncTask(this);
                task.execute(null, Variables.POSTUSER,"POST",mUserToSent.objectToNameValuePair());
                task.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
                    @Override
                    public void setMyTaskComplete(String message){
                        Toast.makeText(getLocalContext(),getString(R.string.user_saved),Toast.LENGTH_LONG).show();
                    }
                });
                SharedPreferences preferences = getSharedPreferences(Variables.SURICAPPREFERENCES,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor  = preferences.edit();
                editor.putString("categories",getCheckBoxChoice());
                editor.putInt("rayon", mProgress);
                editor.putString("userLog", mUserToSent.getUser_pseudo());
                editor.apply();
                setResult(Variables.REQUEST_EXIT_GOOD);
                this.finish();
                break;
        }
    }

    /**
     * Transform checkbox choice in string value
     * @return
     */
    private String getCheckBoxChoice()
    {
        StringBuilder sb = new StringBuilder("1");
        if(mTransportCheckBox.isChecked())
            sb.append("-2");
        if(mPromotionCheckBox.isChecked())
            sb.append("-3");
        if(mRencontreCheckBox.isChecked())
            sb.append("-4");
        if(mEventCheckBox.isChecked())
            sb.append("-5");
        if(mAutoriteCheckBox.isChecked())
            sb.append("-6");
        if(mCovoiturageCheckBox.isChecked())
            sb.append("-7");
        return sb.toString();
    }

    private Context getLocalContext()
    {
        return this;
    }
}
