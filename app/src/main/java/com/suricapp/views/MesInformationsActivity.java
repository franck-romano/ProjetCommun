package com.suricapp.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.suricapp.models.User;
import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.tools.DialogCreation;
import com.suricapp.tools.ImageManipulation;
import com.suricapp.tools.StringValidator;
import com.suricapp.tools.Variables;
import com.suricapp.tools.ViewModification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MesInformationsActivity extends ActionBarActivity implements View.OnClickListener{

    private DatePicker datePicker;
    private Calendar calendar;

    // Text View from view
    private TextView mDateView;
    private TextView mLoginTextView;
    private TextView mEmailtexTextView;
    private TextView mCityTextView;
    private TextView mPasswordTextView;
    private TextView mConfirmationTextView;

    /**
     * View to hide or show spinner
     */
    private View mView;
    private View mSpinnerView;


    // Button from view
    private Button mPhotoButton;
    private Button mNextStepButton;
    private TextView txtLabel;
    // Date picker
    private int year, month, day;
    private static int _MAJORITY = 18;

    //Image items
    private Uri fileUri;
    // User to send
    private User mUser = new User();
    private boolean changed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/corbel.ttf");
        txtLabel=(TextView)findViewById(R.id.txtViewLabel);
        txtLabel.setTypeface(type);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_informations);

        txtLabel=(TextView)findViewById(R.id.txtViewLabel);
        // Date picker initialization
        mDateView = (TextView) findViewById(R.id.activity_mes_informations_date);
        wakeUpDate();

        //textView initialization
        mLoginTextView = (TextView) findViewById(R.id.activity_mes_informations_pseudo);
        mEmailtexTextView = (TextView) findViewById(R.id.activity_mes_informations_mail);
        mCityTextView = (TextView) findViewById(R.id.activity_mes_informations_ville);
        mLoginTextView = (TextView) findViewById(R.id.activity_mes_informations_pseudo);
        mPasswordTextView = (TextView) findViewById(R.id.activity_mes_informations_password);
        mConfirmationTextView = (TextView) findViewById(R.id.activity_mes_informations_confirmation);


        // Button settings
        mNextStepButton = (Button) findViewById(R.id.activity_mes_informations_suivant);
        mNextStepButton.setOnClickListener(this);
        mPhotoButton = (Button) findViewById(R.id.activity_mes_informations_photo);
        mPhotoButton.setOnClickListener(this);

        //View for show or not the spinner
        mView = findViewById(R.id.activity_mes_informations_view);
        mSpinnerView = findViewById(R.id.activity_mes_informations_status);

        mUser = new User();
        mUser.setUser_id(Integer.parseInt(getSharedPreferences(Variables.SURICAPPREFERENCES, Context.MODE_PRIVATE).getString("userLogId", "0")));
        loadUserFromWeb();

    }

    private void loadUserFromWeb()
    {
        ViewModification.showProgress(true, mSpinnerView, mView, this);
        HTTPAsyncTask taskPseudo = new HTTPAsyncTask(getLocalContext());
        taskPseudo.execute(null, Variables.GETUSERWITHID+mUser.getUser_id(), "GET", null);
        taskPseudo.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String result) {
                JSONArray jarray = null;
                try
                {
                    jarray = new JSONArray(result);
                    JSONObject jsonObject = jarray.getJSONObject(0);
                    mUser = new User();
                    mUser.setUser_pseudo(jsonObject.getString("user_pseudo"));
                    mUser.setUser_id(Integer.parseInt(jsonObject.getString("user_id")));
                    mUser.setUser_picture(jsonObject.getString("user_picture"));
                    mUser.setUser_email(jsonObject.getString("user_email"));
                    mUser.setUser_city(jsonObject.getString("user_city"));
                    mUser.setUser_password(jsonObject.getString("user_password"));
                    mLoginTextView.setText(mUser.getUser_pseudo());
                    mEmailtexTextView.setText(mUser.getUser_email());
                    mCityTextView.setText(mUser.getUser_city());
                    ViewModification.showProgress(false, mSpinnerView, mView, getLocalContext());
                } catch (Exception e) {
                    Log.w("EXCEPTION1", e.toString());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_mes_informations_suivant:
                // Check all form field requierement
                StringValidator testString = new StringValidator();
                if( mLoginTextView.getText().toString().trim().matches("")
                        || mEmailtexTextView.getText().toString().trim().matches("")
                        || mCityTextView.getText().toString().trim().matches("")
                        || mDateView.getText().toString().trim().matches(""))
                {
                    DialogCreation.createDialog(this,getString(R.string.champs_requis),getString(R.string.champs_requis_desc));
                    return;
                }
                else {
                    if(!mCityTextView.getText().toString().equals(mUser.getUser_city())) {
                        mUser.setUser_city(mCityTextView.getText().toString());
                        changed =true;
                    }
                    mUser.setUser_birthday(new Date(year,month,day));
                    if(!mPasswordTextView.getText().toString().trim().matches("")
                            || !mConfirmationTextView.getText().toString().trim().matches(""))
                    {
                        if(!mPasswordTextView.getText().toString().equals(mConfirmationTextView.getText().toString()))
                        {
                            DialogCreation.createDialog(this,getString(R.string.differentPwd),getString(R.string.differentPwd_desc));
                            return;
                        }else if(!testString.validatePassword(mPasswordTextView.getText().toString()))
                        {
                            DialogCreation.createDialog(this,getString(R.string.bad_password),getString(R.string.bad_password_desc));
                            return;
                        }
                        try {
                            mUser.setUser_password(StringValidator.SHA1(mPasswordTextView.getText().toString()));
                            changed =true;
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    if(changed)
                    {
                        HTTPAsyncTask taskPseudo= new HTTPAsyncTask(this);
                        taskPseudo.execute(null,Variables.PUTUSER+mUser.getUser_id(),"PUT",mUser.objectToNameValuePair());
                        taskPseudo.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
                            @Override
                            public void setMyTaskComplete(String result) {
                                ((Activity)getLocalContext()).finish();
                            }
                        });
                    }
                    else
                        finish();
                }
                break;
            case R.id.activity_mes_informations_photo :
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.photo_method)).setMessage(R.string.photo_method_desc);
                builder.setPositiveButton(getString(R.string.galerie), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGallery();
                    }
                });
                builder.setNegativeButton(getString(R.string.take_a_picture), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startCamera();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            }
        }
    }


    /**
     *
     */
    private void startCamera()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(Variables.MEDIA_TYPE_IMAGE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        startActivityForResult(takePictureIntent, Variables.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    /**
     *
     */
    private void startGallery()
    {
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, Variables.GALLERY_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // When return from camera intent or gallery intent
        if(requestCode == Variables.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Uri selectedImage = fileUri;
                getContentResolver().notifyChange(selectedImage, null);
                ContentResolver cr = getContentResolver();
                Bitmap bitmap;
                try {
                    bitmap = android.provider.MediaStore.Images.Media
                            .getBitmap(cr, selectedImage);
                    mUser.setUser_picture(ImageManipulation.encodeImage(ImageManipulation.transformBitmapToArrayByte(scaleDownBitmap(bitmap, 100, getLocalContext()))));
                    changed =true;
                    Toast.makeText(this, R.string.picture_saved, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                            .show();
                    Log.e("Camera", e.toString());
                }
            }
        }
        if (requestCode == Variables.GALLERY_IMAGE_ACTIVITY_REQUEST_CODE) {
            if(resultCode == RESULT_OK)
            {
                // Get photo from gallery
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                // transform photo as string to save it in BDD
                Bitmap image = BitmapFactory.decodeFile(picturePath);
                mUser.setUser_picture(ImageManipulation.encodeImage(ImageManipulation.transformBitmapToArrayByte(scaleDownBitmap(image,100,getLocalContext()))));
                changed =true;
                Toast.makeText(this,R.string.picture_saved,Toast.LENGTH_LONG).show();
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
        month = 01;
        day = 01;
        showDate(year-_MAJORITY, month, day);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year-_MAJORITY, month, day);
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
            showDate(arg1, arg2, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        mDateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private Context getLocalContext()
    {
        return this;
    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        File mediaFile;
        if (type == Variables.MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == Variables.MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    /**
     * Get a smaller picture
     * @param photo
     * @param newHeight
     * @param context
     * @return
     */
    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

}
