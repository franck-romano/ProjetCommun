package com.suricapp.projetcommun;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class CategoriesActivity extends ActionBarActivity {

    //Variables pour le menu
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    private void addDrawerItems(){
        String[] itemArray = { getString(R.string.new_message), getString(R.string.timeline_view), getString(R.string.map_view), getString(R.string.my_profile), getString(R.string.my_followers), getString(R.string.my_categories), getString(R.string.my_informations), getString(R.string.deconnexion) };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent message = new Intent(CategoriesActivity.this, NewMessage.class);
                        startActivity(message);
                        break;
                    case 1:
                        Intent timeline = new Intent(CategoriesActivity.this, TimelineActivity.class);
                        startActivity(timeline);
                        break;
                    case 2:
                        Intent map = new Intent(CategoriesActivity.this, MapActivity.class);
                        startActivity(map);
                        break;
                    case 3:
                        Intent profil = new Intent(CategoriesActivity.this, ProfilActivity.class);
                        startActivity(profil);
                        break;
                    /*case 3:
                        Intent followers = new Intent(NewMessage.this, followers.class);
                        startActivity(followers);
                        break;*/
                    case 5:
                        Intent categorie = new Intent(CategoriesActivity.this, CategoriesActivity.class);
                        startActivity(categorie);
                        break;
                    /*case 5:
                        Intent informations = new Intent(NewMessage.this, Information.class);
                        startActivity(informations);
                        break;*/
                    /*case 6:
                        Intent deconnexion = new Intent(NewMessage.this, deconnexion.class);
                        startActivity(deconnexion);
                        break;*/
                }
            }
        });
    }

    private void setupDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(getString(R.string.menu));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}