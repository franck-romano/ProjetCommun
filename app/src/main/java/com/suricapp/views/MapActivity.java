package com.suricapp.views;

import android.content.Intent;
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suricapp.tools.Variables;


public class MapActivity extends FragmentActivity implements LocationListener{

    /**
     * Variables pour Google Maps
     */
    private GoogleMap googleMap;
    private LocationManager locationManager;

    //Variables pour le menu
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        setupDrawer();
        addDrawerItems();

        createMapView();

        //Changement de vue vers la vue NewMessage
        ImageButton bouton = (ImageButton)findViewById(R.id.new_message);

        bouton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, NewMessage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        //Obtention de la référence du service
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        //Si le GPS est disponible, on s'y abonne
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            abonnementGPS();
        }
    }

    /**
     * Méthode permettant de s'abonner à la localisation par GPS.
     */
    public void abonnementGPS() {
        //On s'abonne
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }

    public void desabonnementGPS() {
        //Si le GPS est disponible, on s'y abonne
        locationManager.removeUpdates(this);
    }


    /**
     * Initialises the mapview
     */
    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the
         */
        try {
            if(googleMap == null){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMap();
                //Permet d'afficher le bouton MaPosition
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                //Autoriser la récupération de la position actuelle.
                googleMap.setMyLocationEnabled(true);

                /**
                 * Si la Map est toujours null après initialisation on affiche une erreur via un Toast
                 */
                if(googleMap == null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.erreur_map), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    /**
     * Adds a marker to the map
     */
    private void addMarker(int latitude, int longitude) {
        if (googleMap != null) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(getString(R.string.here)));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
    }

    @Override
    public void onProviderDisabled(final String provider) {
        //Si le GPS est désactivé on se désabonne
        if("gps".equals(provider)) {
            desabonnementGPS();
        }
    }

    @Override
    public void onProviderEnabled(final String provider) {
        //Si le GPS est activé on s'abonne
        if("gps".equals(provider)) {
            abonnementGPS();
        }
    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) { }

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
    @Override
    protected void onPause() {
        super.onPause();
        //On appelle la méthode pour se désabonner
        desabonnementGPS();
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
