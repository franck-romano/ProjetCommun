package com.suricapp.views;

import android.os.Bundle;
import android.view.Menu;


public class CategoriesActivity extends SuricappActionBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_categories);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_message, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
