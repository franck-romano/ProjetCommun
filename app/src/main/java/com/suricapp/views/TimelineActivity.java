package com.suricapp.views;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.suricapp.models.LocationBetween;
import com.suricapp.models.Message;
import com.suricapp.models.User;
import com.suricapp.models.UserMessageTimeline;
import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.tools.LocationUsage;
import com.suricapp.tools.Variables;
import com.suricapp.tools.ViewModification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimelineActivity extends SuricappActionBar {

    private ArrayList<Message> allMessages;

    //Variables pour la listView
    private MessageListAdapter messageAdapter;
    private ListView messageList;


    /**
     * View to hide or show spinner
     */
    private View mView;
    private View mSpinnerView;

    LocationBetween mLocationBetween;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.activity_timeline);
        super.onCreate(savedInstanceState);

        // List View
        messageList = (ListView)findViewById(R.id.timeline_listView);
        messageList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        messageAdapter = new MessageListAdapter(this,R.layout.activity_timeline_row);
        messageList.setAdapter(messageAdapter);

        //View for show or not the spinner
        mView = findViewById(R.id.activity_timeline_view);
        mSpinnerView = findViewById(R.id.activity_timeline_status);
        loadMessage();

    }

    public void loadMessage()
    {
        ViewModification.showProgress(true,mSpinnerView,mView,this);

        Location myLocation = LocationUsage.getLastKnownLocation(this);
        // Check if location is available
        if(myLocation == null)
            LocationUsage.buildAlertMessageNoGps(this);
        else {
            mLocationBetween = LocationUsage.getLocationBetween(myLocation,this);
            getAllMessageForUserCategorie();
        }
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

        taskMessage.execute(null,"http://suricapp.esy.es/wsa.php/d_message/?message_longitude[gt]="+mLocationBetween.getLongitudePoint1()+
                "&message_longitude[lt]="+mLocationBetween.getLongitudePoint2()+"&message_latitude[lt]="+mLocationBetween.getLatitudePoint2()
                +"&message_latitude[gt]="+mLocationBetween.getLatitudePoint1()+"&message_id_category_fk[in]="+sb.toString()
                +"&order=message_date&orderType=DESC","GET",null);
        Log.w("URL","http://suricapp.esy.es/wsa.php/d_message/?message_longitude[gt]="+mLocationBetween.getLongitudePoint1()+
                "&message_longitude[lt]="+mLocationBetween.getLongitudePoint2()+
                "&message_latitude[lt]="+mLocationBetween.getLatitudePoint2() +
                "&message_latitude[gt]="+mLocationBetween.getLatitudePoint1()+
                "&message_id_category_fk[in]="+sb.toString()
                +"&order=message_date&orderType=DESC");
//        taskMessage.execute(null,"http://suricapp.esy.es/wsa.php/d_message/"+
//                "?message_id_category_fk[in]="+sb.toString()+"&order=message_date&orderType=DESC","GET",null);
        taskMessage.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String result) {
                try {
                    JSONArray jarray = new JSONArray(result);
                    StringBuilder sb = new StringBuilder(Integer.parseInt(jarray.getJSONObject(0).getString("message_id_user_fk")));
                    for (int i = 0; i < jarray.length() && i < 20; i++) {
                        UserMessageTimeline umtl = new UserMessageTimeline();
                        JSONObject jsonObject = jarray.getJSONObject(i);
                        Message m = new Message();
                        m.setMessage_id_user_fk(Integer.parseInt(jsonObject.getString("message_id_user_fk")));
                        m.setMessage_id(Integer.parseInt(jsonObject.getString("message_id")));


                        umtl.setmUserId(Integer.parseInt(jsonObject.getString("message_id_user_fk")));
                        umtl.setmMessagePosition(i);
                        userAdd.add(umtl);
                        sb.append("," + Integer.parseInt(jsonObject.getString("message_id_user_fk")));

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
                    Log.w("Retour un","retour un");
                    getUserInfo(sb.toString());
                } catch (JSONException e) {
                    Log.w("BAD1", e.toString());
                } catch (ParseException e) {
                    Log.w("BAD2", e.toString());
                } catch (Exception e) {
                    Log.w("BAD3", e.toString());
                }
            }
        });
    }



    ArrayList<UserMessageTimeline> userAdd = new ArrayList<>();
    private void getUserInfo(String req) {
        HTTPAsyncTask taskPseudo = new HTTPAsyncTask(getLocalContext());
        taskPseudo.execute(null, Variables.GETMULTIPLEUSERWITHID +req, "GET", null);
        taskPseudo.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String result) {
                JSONArray jarray = null;
                try {

                    jarray = new JSONArray(result);
                    for (int i=0;i<jarray.length();i++)
                    {
                        setUserToMessage(jarray.getJSONObject(i));
                    }
                    messageAdapter.swapItems(allMessages);
                    Log.w("Retour deux","retour deux");
                    ViewModification.showProgress(false,mSpinnerView,mView,getLocalContext());
                } catch (Exception e) {
                    Log.w("EXCEPTION", e.toString());
                }
            }
        });
    }

    private void setUserToMessage(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.setUser_pseudo(jsonObject.getString("user_pseudo"));
        user.setUser_id(Integer.parseInt(jsonObject.getString("user_id")));
        user.setUser_picture(jsonObject.getString("user_picture"));
        for(int i=0; i < userAdd.size();i++)
        {
            if(userAdd.get(i).getmUserId() == user.getUser_id()) {
                allMessages.get(userAdd.get(i).getmMessagePosition()).setmUser(user);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Variables.REQUESTLOADMESSAGE)
            loadMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_message, menu);
        return true;
    }

    @Override
    public Context getLocalContext()
    {
        return this;
    }
}

