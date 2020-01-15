package com.suman.googlemap;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suman.googlemap.model.LatitudeLongitude;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<LatitudeLongitude> longitudes = new ArrayList<>();
        longitudes.add(new LatitudeLongitude(27.6623825,85.3034549,"Mero Ghar"));
        longitudes.add(new LatitudeLongitude(27.6618263,85.3034658,"Medicity Hospital"));
        longitudes.add(new LatitudeLongitude(27.6617875,85.3030792,"Big Mart"));
//        longitudes.add(new LatitudeLongitude(27.7052354,85.3294158,"Softwarica College"));
//        longitudes.add(new LatitudeLongitude(27.70482,85.3293997,"Sabin ko Pasal"));

        CameraUpdate center, zoom;
        for(int i=0; i<longitudes.size();i++)
        {
            center = CameraUpdateFactory.newLatLng(new LatLng(longitudes.get(i).getLat(),
                    longitudes.get(i).getLon()));
            zoom =CameraUpdateFactory.zoomTo(16);
            mMap.addMarker(new MarkerOptions().position(new LatLng(longitudes.get(i).getLat(),
                    longitudes.get(i).getLon())).title(longitudes.get(i).getMarker()));

            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }

//        // Add a marker in Sydney and move the camera
//        LatLng nakkhu = new LatLng(27.6623825,85.3034549);
//        mMap.addMarker(new MarkerOptions().position(nakkhu).title("Mero Ghar"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(nakkhu));
    }
}
