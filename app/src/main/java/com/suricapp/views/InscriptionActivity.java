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

import com.suricapp.tools.DialogCreation;
import com.suricapp.tools.StringValidator;

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
        mDateView = (TextView) findViewById(R.id.activity_inscription_date);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_inscription_suivant:

                //HTTPAsyncTask task= new HTTPAsyncTask(this);

                //task.execute(null,"http://suricapp.esy.es/ws.php/d_user/user_/mailsdugars","GET",null);
                /**
                 task.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
                @Override
                public void setMyTaskComplete(String message) {
                id.setText(message);
                }
                });*/
                StringValidator testString = new StringValidator();
                if(mPasswordTextView.getText().toString().trim().matches("")
                        || mLoginTextView.getText().toString().trim().matches("")
                        || mEmailtexTextView.getText().toString().trim().matches("")
                        || mCityTextView.getText().toString().trim().matches("")
                        || mDateView.getText().toString().trim().matches("")
                        || mConfirmationTextView.getText().toString().trim().matches(""))
                {
                    DialogCreation.createDialog(this,getString(R.string.champs_requis),getString(R.string.champs_requis_desc));
                }
                else if(!testString.validateEmail(mEmailtexTextView.getText().toString()))
                {
                DialogCreation.createDialog(this,getString(R.string.bad_email),getString(R.string.bad_email_desc));
                }
                else if(!mPasswordTextView.getText().toString().equals(mConfirmationTextView.getText().toString()))
                {
                    DialogCreation.createDialog(this,getString(R.string.differentPwd),getString(R.string.differentPwd_desc));
                }
                else if(!testString.validatePassword(mPasswordTextView.getText().toString()))
                {
                    DialogCreation.createDialog(this,getString(R.string.bad_password),getString(R.string.bad_password_desc));
                }
                else
                {
                    Intent intent = new Intent(InscriptionActivity.this, Inscription_2Activity.class);
                    startActivity(intent);
                    break;
                }
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