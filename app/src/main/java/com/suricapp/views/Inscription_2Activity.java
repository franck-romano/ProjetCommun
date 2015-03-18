package com.suricapp.views;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;


public class Inscription_2Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_2);

        SeekBar rayon = (SeekBar)findViewById(R.id.rayon);
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
}
