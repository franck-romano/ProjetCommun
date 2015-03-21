package com.suricapp.views;


import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;


public class TimelineActivity extends SuricappActionBar {
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
        messageList.setClickable(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_message, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}

