package com.suricapp.views;


import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.tools.DialogCreation;

import org.json.JSONException;
import org.json.JSONObject;


public class TimelineActivity extends SuricappActionBar {
    //Variables pour la listView
    private MessageListAdapter messageAdapter;
    private ListView messageList;

    TextView mPseudoTextView;
    TextView mHeureTextView;
    TextView mDistanceTextView;
    TextView mContenuTextView;
    TextView mNbJaimeTextView;
    TextView mNbJaimePasTextView;
    TextView mNbVueTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.activity_timeline);
        super.onCreate(savedInstanceState);

        //textView initialization
        mPseudoTextView = (TextView) findViewById(R.id.activity_timeline_pseudo);
        mHeureTextView = (TextView) findViewById(R.id.activity_timeline_heure);
        mDistanceTextView = (TextView) findViewById(R.id.activity_timeline_distance);
        mContenuTextView = (TextView) findViewById(R.id.activity_timeline_contenu);
        mNbJaimeTextView = (TextView) findViewById(R.id.activity_timeline_nbjaime);
        mNbJaimePasTextView = (TextView) findViewById(R.id.activity_timeline_nbjaimepas);
        mNbVueTextView = (TextView) findViewById(R.id.activity_timeline_nbvue);


        HTTPAsyncTask taskMessage= new HTTPAsyncTask(getLocalContext());
        taskMessage.execute(null,"http://suricapp.esy.es/ws.php/d_message/","GET",null);
        taskMessage.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String result){
                JSONObject obj = null;
                try {
                    obj = new JSONObject(result);
                    mContenuTextView.setText(obj.getString("message_content_fr_fr"));
                } catch (JSONException e) {
                    DialogCreation.createDialog(getLocalContext(), getString(R.string.activity_timeline_title), getString(R.string.activity_timeline_title));
                    mContenuTextView.requestFocus();
                }
            }
        });
        // List View
        messageList = (ListView)findViewById(R.id.timeline_listView);
        messageList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        messageAdapter = new MessageListAdapter(this,R.layout.activity_timeline_row);
        messageList.setAdapter(messageAdapter);
        messageList.setClickable(true);
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
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}

