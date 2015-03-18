package com.suricapp.views;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by maxence on 18/03/15.
 */
public class SuricAppActionBarActivity extends ActionBarActivity{


    //Variables pour le menu
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater)getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_suric_app_action_bar, null);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        if(mDrawerLayout== null)
            Log.w("LA NULLITÉ","LA NULLITÉ");
        if(mDrawerList== null)
            Log.w("LA NULLITÉ","LA NULLITÉ");

        setupDrawer();
        addDrawerItems();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void setupDrawer(){
        Log.w("NULL","NULL");
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                Log.w("NULL","NULL");
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                Log.w("NULL","NULL");
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("Suric'app");
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

    private void addDrawerItems(){
        String[] itemArray = { "Nouveau Message", "Vue Timeline", "Vue Carte", "Mon profil", "Mes suiveurs", "Mes catégories", "Mes informations", "Déconnexion" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent message = new Intent(getApplicationContext(), NewMessage.class);
                        startActivity(message);
                        break;
                    case 1:
                        Intent timeline = new Intent(getApplicationContext(), TimelineActivity.class);
                        startActivity(timeline);
                        break;
                    case 2:
                        Intent map = new Intent(getApplicationContext(), MapActivity.class);
                        startActivity(map);
                        break;
                    case 3:
                        Intent profil = new Intent(getApplicationContext(), ProfilActivity.class);
                        startActivity(profil);
                        break;
                    /*case 3:
                        Intent followers = new Intent(.this, followers.class);
                        startActivity(followers);
                        break;*/
                    case 5:
                        Intent categorie = new Intent(getApplicationContext(), CategoriesActivity.class);
                        startActivity(categorie);
                        break;
                    /*case 5:
                        Intent informations = new Intent(.this, Information.class);
                        startActivity(informations);
                        break;*/
                    /*case 6:
                        Intent deconnexion = new Intent(.this, deconnexion.class);
                        startActivity(deconnexion);
                        break;*/
                }
            }
        });
    }
}
