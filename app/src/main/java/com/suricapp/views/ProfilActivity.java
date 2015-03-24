package com.suricapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;


public class ProfilActivity extends SuricappActionBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profil);
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_message, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent timeline = new Intent(getApplicationContext(), TimelineActivity.class);
        startActivity(timeline);
    }
}
