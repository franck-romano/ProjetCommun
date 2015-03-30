package com.suricapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.suricapp.tools.Variables;

/**
 * Created by maxence on 20/03/15.
 */
public class SuricappActionBar extends Activity {
    //Variables pour le menu
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        setupDrawer();
        addDrawerItems();
    }



    private void setupDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle(getString(R.string.menu_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(getString(R.string.app_name));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Context getLocalContext()
    {
        return this;
    }

    private void addDrawerItems(){
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Variables.itemArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent message = new Intent(getApplicationContext(), NewMessage.class);
                        startActivityForResult(message,Variables.REQUEST_CODE_MESSAGE_ACTIVITY);
                        break;
                    case 1:
                        if(view.getContext() == getLocalContext())
                        {
                            mDrawerLayout.closeDrawers();
                            return;
                        }
                        Intent timeline = new Intent(getApplicationContext(), TimelineActivity.class);
                        startActivity(timeline);
                        break;
                    case 2:
                        Intent map = new Intent(getApplicationContext(), MapActivity.class);
                        startActivity(map);
                        break;
                    case 3:
                        Intent profil = new Intent(getApplicationContext(),ProfilActivity.class);
                        startActivity(profil);
                        break;
                    case 4:
                        Intent categorie = new Intent(getApplicationContext(), CategoriesActivity.class);
                        startActivityForResult(categorie, Variables.REQUEST_CODE_CATEGORY_ACTIVITY);
                        break;
                    case 5:
                        Intent connexion = new Intent(SuricappActionBar.this, ConnexionActivity.class);
                        SharedPreferences preferences = getSharedPreferences(Variables.SURICAPPREFERENCES, Context.MODE_PRIVATE);
                        preferences.edit().remove("userLog").commit();
                        preferences.edit().remove("rayon").commit();
                        preferences.edit().remove("userLogId").commit();
                        preferences.edit().remove("categories").commit();
                        startActivity(connexion);
                        finish();
                        break;
                }
                mDrawerLayout.closeDrawers();
            }
        });
    }
}
