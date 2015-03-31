package com.suricapp.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suricapp.models.LocationBetween;
import com.suricapp.models.Message;
import com.suricapp.models.User;
import com.suricapp.models.UserMessageTimeline;
import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.tools.DateManipulation;
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
import java.util.HashMap;
import java.util.WeakHashMap;


public class MapActivity extends FragmentActivity implements LocationListener,GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

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
    private ArrayList<Message> allMessages;
    private LatLng coordMessage;
    private HashMap<String, Message> hasMap = new HashMap<>();
    private LocationBetween mLocationBetween;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        loadMessage();
        setupDrawer();
        addDrawerItems();
        createMapView();
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
        //googleMap.animateCamera( CameraUpdateFactory.zoomTo( 7.0f ) );
        //Changement de vue vers la vue NewMessage
        ImageButton bouton = (ImageButton)findViewById(R.id.new_message);
        getAllMessageForUserCategorie();
        bouton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, NewMessage.class);
                startActivity(intent);
            }
        });
       // messageAdapter = new MessageListAdapter(this,R.layout.activity_timeline_row);
    }

    public void loadMessage()
    {
        Location myLocation = LocationUsage.getLastKnownLocation(this);
        // Check if location is available
        if(myLocation == null)
            LocationUsage.buildAlertMessageNoGps(this);
        else {
            mLocationBetween = LocationUsage.getLocationBetween(myLocation,this);
            getAllMessageForUserCategorie();
        }
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
                   /* case 3:
                        Intent profil = new Intent(getApplicationContext(), ProfilActivity.class);
                        startActivity(profil);
                        break;
                    /*case 3:
                        Intent followers = new Intent(.this, followers.class);
                        startActivity(followers);
                        break;*/
                    case 3:
                        Intent categorie = new Intent(getApplicationContext(), CategoriesActivity.class);
                        startActivity(categorie);
                        break;
                    /*case 5:
                        Intent informations = new Intent(.this, Information.class);
                        startActivity(informations);
                        break;*/
                    case 4:
                        Intent connexion = new Intent(MapActivity.this, ConnexionActivity.class);
                        SharedPreferences preferences = getSharedPreferences(Variables.SURICAPPREFERENCES, Context.MODE_PRIVATE);
                        preferences.edit().remove("userLog").commit();
                        startActivity(connexion);
                        break;
                }
            }
        });
    }



    private void getAllMessageForUserCategorie() {

        allMessages = new ArrayList<>();
        userAdd = new ArrayList<>();
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

                        coordMessage= new LatLng(m.getMessage_latitude(), m.getMessage_longitude());
                        Marker marker = googleMap.addMarker(new MarkerOptions()
                                .position(coordMessage)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                .snippet(m.getMessage_title_fr_fr())
                                .title("Posté" + DateManipulation.timespanToStringWithoutA(m.getMessage_date())));
                        hasMap.put(marker.getId(),m);
                        Log.w("Message content ",m.getMessage_content_fr_fr());
                        allMessages.add(m);
                    }
                    getUserInfo(sb.toString());
                } catch (JSONException e) {
                } catch (ParseException e) {
                } catch (Exception e) {
                }
            }
        });
    }


    public Context getLocalContext()
    {
        return this;
    }

    ArrayList<UserMessageTimeline> userAdd;
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
                    Log.w("Retour deux","retour deux");
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent timeline = new Intent(getApplicationContext(), TimelineActivity.class);
        startActivity(timeline);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    public Message findMarkerMessage(Marker marker){
        String idMarker = marker.getId();
        Message msg =hasMap.get(idMarker);
        Log.w("Message ",msg.getMessage_content_fr_fr());
        Message msgToReach =new Message();
        for(Message m: allMessages){
              if(m.getMessage_id() == msg.getMessage_id()){
                  msgToReach=msg;
                  Log.w("TOUTITOITIU",msgToReach.getMessage_title_fr_fr());
                  break;
              }
        }
        return msgToReach;
    }
    private void launchDetailMessageView(Message mess)
    {
        Intent detailIntent = new Intent(this, DetailMessageActivity.class);
        detailIntent.putExtra("message",mess);
        startActivity(detailIntent);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Message msgToReach;
        msgToReach= findMarkerMessage(marker);
        if(msgToReach != null) {
            launchDetailMessageView(msgToReach);
        }
    }
}
