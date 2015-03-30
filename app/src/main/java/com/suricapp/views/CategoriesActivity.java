package com.suricapp.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.suricapp.tools.DialogCreation;
import com.suricapp.tools.Variables;


public class CategoriesActivity extends ActionBarActivity implements View.OnClickListener{

    // Check box
    private CheckBox mTransportCheckBox;
    private CheckBox mPromotionCheckBox;
    private CheckBox mCovoiturageCheckBox;
    private CheckBox mEventCheckBox;
    private CheckBox mRencontreCheckBox;
    private CheckBox mAutoriteCheckBox;

    // Button
    private Button mEnvoyerButton;

    // Progress bar
    private int mProgress = 0;
    private SeekBar rayon;
    private TextView rayon_numeric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_categories);
        super.onCreate(savedInstanceState);

        //View settings
        mTransportCheckBox = (CheckBox) findViewById(R.id.activity_categories_categorie_transport);
        mPromotionCheckBox = (CheckBox) findViewById(R.id.activity_categories_categorie_promotion);
        mCovoiturageCheckBox= (CheckBox) findViewById(R.id.activity_categories_categorie_covoiturage);
        mEventCheckBox= (CheckBox) findViewById(R.id.activity_categories_categorie_evenement);
        mRencontreCheckBox= (CheckBox) findViewById(R.id.activity_categories_categorie_rencontre);
        mAutoriteCheckBox= (CheckBox) findViewById(R.id.activity_categories_categorie_autorites);
        mEnvoyerButton = (Button) findViewById(R.id.activity_categories_envoyer);
        mEnvoyerButton.setOnClickListener(this);

        // check correct box
        checkCorrectBox();

        rayon_numeric = (TextView)findViewById(R.id.rayon_texte);
        rayon = (SeekBar)findViewById(R.id.activity_inscription_2rayon);
        setUpSeekBar();

        if(getIntent().hasExtra("fromConnexion"))
        {
            DialogCreation.createDialog(this,getString(R.string.preference),getString(R.string.preference_desc));
        }
    }

    private void setUpSeekBar()
    {
        SharedPreferences preferences = getSharedPreferences(Variables.SURICAPPREFERENCES,Context.MODE_PRIVATE);
        if(preferences.contains("rayon"))
        {
            mProgress = preferences.getInt("rayon",2);
            // Initialiser le textView avec la valeur de départ.
            rayon.setProgress(mProgress);
            rayon_numeric.setText(rayon.getProgress() + " kms");
        }
        else {
            // Initialiser le textView avec la valeur de départ.
            rayon_numeric.setText(rayon.getProgress() + " kms");
            mProgress = rayon.getProgress();
        }
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

    /**
     * check the correct box
     */
    private void checkCorrectBox() {
        SharedPreferences preferences = getSharedPreferences(Variables.SURICAPPREFERENCES, Context.MODE_PRIVATE);
        String tosplit = preferences.getString("categories","0");
        String categ[] = tosplit.split("-");
        for (int i =0; i < categ.length; i++)
        {
            switch (Integer.parseInt(categ[i]))
            {
                case 2 :
                    mTransportCheckBox.setChecked(true);
                    break;
                case 3 :
                    mPromotionCheckBox.setChecked(true);
                    break;
                case 4 :
                    mRencontreCheckBox.setChecked(true);
                    break;
                case 5 :
                    mEventCheckBox.setChecked(true);
                    break;
                case 6 :
                    mAutoriteCheckBox.setChecked(true);
                    break;
                case 7 :
                    mCovoiturageCheckBox.setChecked(true);
                    break;
            }
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

    private Context getLocalContext(){return this;}

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_categories_envoyer :
                if (!doesOneCategorieChecked())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(getString(R.string.no_category_selected)).setMessage(R.string.no_category_selected_desc);
                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveCategoriesAndRayon();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    saveCategoriesAndRayon();
                }
                break;
        }
    }

    private void saveCategoriesAndRayon()
    {
        SharedPreferences preferences = getSharedPreferences(Variables.SURICAPPREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = preferences.edit();
        editor.putString("categories",getCheckBoxChoice());
        editor.putInt("rayon", mProgress);
        editor.apply();
        Toast.makeText(this, getString(R.string.preferences_saved), Toast.LENGTH_LONG).show();
        if(getIntent().hasExtra("fromConnexion"))
        {
            Intent intent = new Intent(getApplicationContext(),TimelineActivity.class);
            startActivity(intent);
        }
        setResult(Variables.REQUESTLOADMESSAGE);
        this.finish();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
