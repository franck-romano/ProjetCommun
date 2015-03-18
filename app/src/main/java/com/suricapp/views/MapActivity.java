package com.suricapp.views;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends FragmentActivity{

    /**
     * Variables pour Google Maps
     */
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Google Map
        createMapView();
        addMarker(45, 0);

        //Changement de vue
       ImageButton bouton = (ImageButton)findViewById(R.id.new_message);

        bouton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, NewMessage.class);
                startActivity(intent);
            }
        });
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
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMap();
                /*Permet d'activer le zoom sur l'UI*/
                //googleMap.getUiSettings().setZoomControlsEnabled(true);
                /*Permet d'afficher le compas sur l'UI*/
                googleMap.getUiSettings().setCompassEnabled(true);
                /*Permet d'afficher le bouton MaPosition*/
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);

                /**
                 * If the  is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(), "Error creating ", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    /**
     * Adds a marker to the map
     */
    private void addMarker(int longitude, int latitude) {
        if (null != googleMap) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(longitude, latitude)).title("Point X"));
        }
    }
}
