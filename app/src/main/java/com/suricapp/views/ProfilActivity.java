package com.suricapp.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.suricapp.models.Message;
import com.suricapp.models.User;
import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.tools.ImageManipulation;
import com.suricapp.tools.Variables;
import com.suricapp.tools.ViewModification;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ProfilActivity extends SuricappActionBar {

    // Usefull item for list view
    private ArrayList<Message> allMessages;

    //Variables pour la listView
    private MessageProfilListAdapter messageAdapter;
    private ListView messageList;


    /**
     * View to hide or show spinner
     */
    private View mView;
    private View mSpinnerView;


    //User to show id
    private User mUser;

    //View items
    private ImageView mPhotoImageView;
    private TextView mPseudoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profil);
        super.onCreate(savedInstanceState);

        //All messages intialisation
        allMessages = new ArrayList<>();

        // List View
        messageList = (ListView)findViewById(R.id.activity_profil_listView);
        messageList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // adapter for list View
        messageAdapter = new MessageProfilListAdapter(this,R.layout.activity_profil_list_row);
        messageList.setAdapter(messageAdapter);

        //View for show or not the spinner
        mView = findViewById(R.id.activity_profil_listView);
        mSpinnerView = findViewById(R.id.activity_profil_status);

        // View conf
        mPseudoTextView = (TextView) findViewById(R.id.activity_profil_pseudo);
        mPhotoImageView = (ImageView) findViewById(R.id.activity_profil_photo);



        // Check the user that view have to show
        if(getIntent().hasExtra("user")) {
            mUser = (User) getIntent().getSerializableExtra("user");
            loadUserLocal();
        }
        else {
            mUser = new User();
            mUser.setUser_id(Integer.parseInt(getSharedPreferences(Variables.SURICAPPREFERENCES, Context.MODE_PRIVATE).getString("userLogId", "0")));
            loadUserFromWeb();
        }

        // Load user and message
        loadMessage();
        loadNbFollowers();

    }

    private void launchProfilView()
    {
        Intent intent = new Intent(this,ProfilActivity.class);
        intent.putExtra("user",mUser);
        startActivity(intent);
    }

    private void loadUserLocal() {
        if(!mUser.getUser_picture().trim().matches(""))
        {
            byte imageByte[] = ImageManipulation.decodeImage(mUser.getUser_picture());
            mPhotoImageView.setImageBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
        }
        mPseudoTextView.setText(mUser.getUser_pseudo());
    }

    private void loadNbFollowers() {

    }

    private void loadUserFromWeb()
    {
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
                    if(!jsonObject.getString("user_picture").trim().matches(""))
                    {
                        byte imageByte[] = ImageManipulation.decodeImage(jsonObject.getString("user_picture"));
                        mPhotoImageView.setImageBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
                        mUser.setUser_picture(jsonObject.getString("user_picture"));
                    }
                    mUser.setUser_pseudo(jsonObject.getString("user_pseudo"));
                    mPseudoTextView.setText(jsonObject.getString("user_pseudo"));
                } catch (Exception e) {
                    Log.w("EXCEPTION1", e.toString());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Variables.REQUESTLOADMESSAGE)
            loadMessage();
    }

    private void loadMessage()
    {
        ViewModification.showProgress(true, mSpinnerView, mView, getLocalContext());
        HTTPAsyncTask taskPseudo = new HTTPAsyncTask(getLocalContext());
        taskPseudo.execute(null, Variables.GETMESSAGEFROMUSER+mUser.getUser_id()+ Variables.ORDERBYDATEDESC, "GET", null);
        taskPseudo.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String result) {
                JSONArray jarray = null;
                try
                {
                    jarray = new JSONArray(result);
                    for(int i =0;i<jarray.length() && i <20 ; i++)
                    {
                        JSONObject jsonObject = jarray.getJSONObject(i);
                        Message mess = new Message();
                        mess.setMessage_nb_like(Integer.parseInt(jsonObject.getString("message_nb_like")));
                        mess.setMessage_id(Integer.parseInt(jsonObject.getString("message_id")));
                        mess.setMessage_nb_unlike(Integer.parseInt(jsonObject.getString("message_nb_unlike")));
                        mess.setMessage_latitude(Double.parseDouble(jsonObject.getString("message_latitude")));
                        mess.setMessage_longitude(Double.parseDouble(jsonObject.getString("message_longitude")));
                        mess.setMessage_content_fr_fr(jsonObject.getString("message_content_fr_fr"));
                        mess.setMessage_id_category_fk(Integer.parseInt(jsonObject.getString("message_id_category_fk")));
                        mess.setMessage_title_fr_fr(jsonObject.getString("message_title_fr_fr"));
                        mess.setmUser(mUser);
                        mess.setMessage_id_user_fk(Integer.parseInt(jsonObject.getString("message_id_user_fk")));

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date parsedDate = dateFormat.parse(jsonObject.getString("message_date"));
                        mess.setMessage_date(new java.sql.Timestamp(parsedDate.getTime()));
                        allMessages.add(mess);
                    }
                    messageAdapter.swapItems(allMessages);
                    ViewModification.showProgress(false,mSpinnerView,mView,getLocalContext());
                } catch (Exception e) {
                    Log.w("EXCEPTION2", e.toString());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        messageAdapter.refreshView();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_message, menu);
        return true;
    }
}
