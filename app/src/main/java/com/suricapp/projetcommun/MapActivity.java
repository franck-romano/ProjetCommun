package com.suricapp.projetcommun;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends FragmentActivity implements LocationListener {

    /**
     * Variables pour Google Maps
     */
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Google Map
        createMapView();
        //addMarker(45, 0);

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
                LocationManager location = (LocationManager) this.getSystemService(LOCATION_SERVICE);
                //Si le GPS est disponible, on s'y abonne
                if(location.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
                }
                /*Permet d'afficher le compas sur l'UI*/
                googleMap.getUiSettings().setCompassEnabled(true);
                /*Permet d'afficher le bouton MaPosition*/
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.setMyLocationEnabled(true);
                googleMap.getMyLocation();

                /**
                 * If the  is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(googleMap == null) {
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
            googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title("Vous êtes ici"));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //Mise à jour des coordonnées
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        marker.setPosition(latLng);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
