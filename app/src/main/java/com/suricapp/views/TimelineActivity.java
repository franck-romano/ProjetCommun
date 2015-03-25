package com.suricapp.views;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

import com.suricapp.models.Category;
import com.suricapp.models.Message;
import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.tools.DialogCreation;
import com.suricapp.tools.LocationUsage;
import com.suricapp.tools.Variables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TimelineActivity extends SuricappActionBar {

    private ArrayList<Message> allMessages;

    //Variables pour la listView
    private MessageListAdapter messageAdapter;
    private ListView messageList;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.activity_timeline);
        super.onCreate(savedInstanceState);

        // List View
        messageList = (ListView)findViewById(R.id.timeline_listView);
        messageList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        messageAdapter = new MessageListAdapter(this,R.layout.activity_timeline_row);
        messageList.setAdapter(messageAdapter);

        Location myLocation = LocationUsage.getLastKnownLocation(this);
        // Check if location is available
        if(myLocation == null)
            LocationUsage.buildAlertMessageNoGps(this);
        else
            getAllMessageForUserCategorie();
    }

    private void getAllMessageForUserCategorie() {
        allMessages = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences(Variables.SURICAPPREFERENCES, Context.MODE_PRIVATE);
        String tosplit = preferences.getString("categories","0");
        String categ[] = tosplit.split("-");
        StringBuilder sb = new StringBuilder(categ[0]);
        for (int i =1; i < categ.length; i++)
        {
            sb.append(","+categ[i]);
        }
            HTTPAsyncTask taskMessage= new HTTPAsyncTask(getLocalContext());
            taskMessage.execute(null,"http://suricapp.esy.es/wsa.php/d_message/" +
                    "?message_id_category_fk[in]="+sb.toString()+"&order=message_date&orderType=DESC","GET",null);
            taskMessage.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
                @Override
                public void setMyTaskComplete(String result){
                    try {
                        JSONArray jarray = new JSONArray(result);
                        for (int i=0;i<jarray.length();i++)
                        {
                            JSONObject jsonObject = jarray.getJSONObject(i);
                            Message m = new Message();
                            m.setMessage_id_user_fk(Integer.parseInt(jsonObject.getString("message_id_user_fk")));
                            Log.w("USER",jsonObject.getString("message_id_user_fk"));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            Date parsedDate = dateFormat.parse(jsonObject.getString("message_date"));
                            m.setMessage_date(new java.sql.Timestamp(parsedDate.getTime()));
                            m.setMessage_latitude(Double.parseDouble(jsonObject.getString("message_latitude")));
                            m.setMessage_longitude(Double.parseDouble(jsonObject.getString("message_longitude")));
                            m.setMessage_title_fr_fr(jsonObject.getString("message_title_fr_fr"));
                            m.setMessage_content_fr_fr(jsonObject.getString("message_content_fr_fr"));
                            m.setMessage_nb_like(Integer.parseInt(jsonObject.getString("message_nb_like")));
                            m.setMessage_nb_unlike(Integer.parseInt(jsonObject.getString("message_nb_unlike")));
                            allMessages.add(m);
                        }
                        messageAdapter.swapItems(allMessages);
                    } catch (JSONException e) {
                        Log.w("BAD","BAD1");
                    } catch (ParseException e) {
                        Log.w("BAD","BAD2");
                        e.printStackTrace();
                    }
                }
            });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_message, menu);
        return true;
    }

    private Context getLocalContext()
    {
        return this;
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }
}

