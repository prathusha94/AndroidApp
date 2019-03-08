package com.example.prathusha.friendfinder;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String name;
    Double Latitude1,Longitude1,Longitude2,Latitude2;
    String[] friends=new String[100];
    String[] lat=new String[100];
    String[] lon=new String[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Bundle extras = getIntent().getExtras();
        name=getIntent().getStringExtra("user");
        Latitude1 = Double.parseDouble(getIntent().getStringExtra("userLat"));
        Longitude1 = Double.parseDouble(getIntent().getStringExtra("userLon"));



        friends=getIntent().getStringArrayExtra("friends");
        lat = getIntent().getStringArrayExtra("f_Lat");
        lon = getIntent().getStringArrayExtra("f_Lon");
       // Latitude2=Double.parseDouble(getIntent().getStringExtra("f1_Lat"));
       // Longitude2=Double.parseDouble(getIntent().getStringExtra("f1_Lon"));

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. 
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker 
        LatLng location = new LatLng(Latitude1, Longitude1);
        mMap.addMarker(new MarkerOptions().position(location).title(name));
        float zoom_level=15;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,zoom_level));

        for(int i=1;i<friends.length;i++) {
            if(lat[i]!=null) {
                //Toast.makeText(getApplicationContext(), lat[i] + "\t" + lon[i], Toast.LENGTH_LONG).show();
                LatLng location1 = new LatLng(Double.valueOf(lat[i]), Double.valueOf(lon[i]));
                 mMap.addMarker(new MarkerOptions().position(location1).title(friends[i]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                 float zoom_level1 = 15;
                 mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, zoom_level1));
            }
        }
    }
}
