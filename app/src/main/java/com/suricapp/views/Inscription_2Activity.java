package com.suricapp.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.suricapp.models.User;
import com.suricapp.rest.client.HTTPAsyncTask;


public class Inscription_2Activity extends ActionBarActivity implements View.OnClickListener{

    private HTTPAsyncTask task;

    private CheckBox m
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_2);

        SeekBar rayon = (SeekBar)findViewById(R.id.activity_inscription_2rayon);
        final TextView rayon_numeric = (TextView)findViewById(R.id.rayon_texte);

        // Initialiser le textView avec la valeur de départ.
        rayon_numeric.setText(rayon.getProgress() + " kms");

        rayon.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                rayon_numeric.setText(progress + " kms");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                rayon_numeric.setText(progress + " kms");
            }
        });
    }

    @Override
    public void onClick(View v) {
        User max = new User();
        switch (v.getId())
        {
            case R.id.action_bar:
                task = new HTTPAsyncTask(this);
                task.execute(null,"http://suricapp.esy.es/ws.php/d_user","POST",max.objectToNameValuePair());
                task.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
                    @Override
                    public void setMyTaskComplete(String message){
                        Toast.makeText(getLocalContext(),getString(R.string.user_saved),Toast.LENGTH_LONG).show();
                    }
                });
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getLocalContext());
                SharedPreferences.Editor editor  = preferences.edit();
                break;
        }
    }

    /**
     * Transform checkbox choice in string value
     * @return
     */
    private String getCheckBoxChoice()
    {
        StringBuilder sb = new StringBuilder();


    }

    private Context getLocalContext()
    {
        return this;
    }
}
