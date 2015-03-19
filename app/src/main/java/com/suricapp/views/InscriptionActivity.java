package com.suricapp.views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;


public class InscriptionActivity extends ActionBarActivity implements View.OnClickListener{

    private DatePicker datePicker;
    private Calendar calendar;

    // Text View from view
    private TextView mDateView;
    private TextView mLoginTextView;
    private TextView mEmailtexTextView;
    private TextView mCityTextView;
    private TextView mPasswordTextView;
    private TextView mConfirmationTextView;

    // Button from view
    private Button mNextStepButton;

    // Date picker
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // Date picker initialization
        mDateView = (TextView) findViewById(R.id.date);
        wakeUpDate();

        //textView initialization
        mLoginTextView = (TextView) findViewById(R.id.activity_inscription_pseudo);
        mEmailtexTextView = (TextView) findViewById(R.id.activity_inscription_mail);
        mCityTextView = (TextView) findViewById(R.id.activity_inscription_ville);
        mLoginTextView = (TextView) findViewById(R.id.activity_inscription_pseudo);
        mPasswordTextView = (TextView) findViewById(R.id.activity_inscription_password);
        mConfirmationTextView = (TextView) findViewById(R.id.activity_inscription_confirmation);

        // Button settings
        mNextStepButton = (Button) findViewById(R.id.activity_inscription_suivant);
        mNextStepButton.setOnClickListener(this);
        Intent intent = new Intent(InscriptionActivity.this, Inscription_2Activity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_inscription_suivant:

                break;
        }
    }

    /**
     * Initialize date picker
     *
     */
    private void wakeUpDate()
    {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener
            = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        mDateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}