package com.suricapp.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.suricapp.tools.Variables;


public class CategoriesActivity extends SuricappActionBar implements View.OnClickListener{

    // Check box
    private CheckBox mTransportCheckBox;
    private CheckBox mPromotionCheckBox;
    private CheckBox mCovoiturageCheckBox;
    private CheckBox mEventCheckBox;
    private CheckBox mRencontreCheckBox;
    private CheckBox mAutoriteCheckBox;

    // Button
    private Button mEnvoyerButton;

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

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
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
                            saveCategories();
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
                    saveCategories();
                }
                break;
        }
    }

    private void saveCategories()
    {
        SharedPreferences preferences = getSharedPreferences(Variables.SURICAPPREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = preferences.edit();
        editor.putString("categories",getCheckBoxChoice());
        editor.apply();
        Toast.makeText(this, getString(R.string.categories_saved), Toast.LENGTH_LONG).show();
        Intent timeline = new Intent(getApplicationContext(), TimelineActivity.class);
        startActivity(timeline);
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
        Intent timeline = new Intent(getApplicationContext(), TimelineActivity.class);
        startActivity(timeline);
    }
}
