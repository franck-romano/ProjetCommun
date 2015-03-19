package com.suricapp.views;

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
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends FragmentActivity implements LocationListener{

    /**
     * Variables pour Google Maps
     */
    private GoogleMap googleMap;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

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

                //Obtention de la référence du service
                locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
                //Si le fournisseur est disponible
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,this);
                }

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
    private void addMarker(int longitude, int latitude) {
        if (null != googleMap) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(getString(R.string.here)));
        }
    }

    @Override
    public void onLocationChanged(final Location location) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.0f));
    }

    @Override
    public void onProviderDisabled(final String provider) {
    }

    @Override
    public void onProviderEnabled(final String provider) {
    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) { }
}
