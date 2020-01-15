package com.suman.googlemap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suman.googlemap.model.LatitudeLongitude;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private AutoCompleteTextView etCity;
    private Button btnsearch;
    private List<LatitudeLongitude> latitudeLongitudes;
    Marker markerName;
    CameraUpdate center,zoom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SupportMapFragment mapFragmen= (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragmen.getMapAsync(this);

        etCity = findViewById(R.id.etCity);
        btnsearch = findViewById(R.id.btnsearch);

        fillArrayListAndSetAdaptar();

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etCity.getText().toString()))
                {
                    etCity.setError("Please enter a place name");
                    return;
                }
                //Get the current location of the place
                int position = SearchArrayList(etCity.getText().toString());
                if(position > -1)
                    loadMap(position);
                else
                    Toast.makeText(SearchActivity.this, "location not found by name :"
                            + etCity.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void loadMap(int position) {

        //Remove old marker from map
        if(markerName!=null)
        {
            markerName.remove();
        }

        double latitude = latitudeLongitudes.get(position).getLat();
        double longitude = latitudeLongitudes.get(position).getLon();
        String marker = latitudeLongitudes.get(position).getMarker();
        center = CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude));
        zoom = CameraUpdateFactory.zoomTo(17);
        markerName = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(marker));
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    // This function  will check  weather the location  is in list or not
    private int SearchArrayList(String name) {
        for(int i=0; i < latitudeLongitudes.size(); i++)
        {
            if (latitudeLongitudes.get(i).getMarker().contains(name)){
                return i;

            }
        }
        return -1;
    }



    //This function will fill arraylist with static  data and set autocomplete with the marker
    private void fillArrayListAndSetAdaptar() {
        latitudeLongitudes = new ArrayList<>();
        latitudeLongitudes.add(new LatitudeLongitude(27.6623825,85.3034549,"Mero Ghar"));
        latitudeLongitudes.add(new LatitudeLongitude(27.6618263,85.3034658,"Medicity Hospital"));
        latitudeLongitudes.add(new LatitudeLongitude(27.6617875,85.3030792,"Big Mart"));

        String[] data = new String[latitudeLongitudes.size()];

        for (int i =0; i< data.length; i++)
        {
            data[i] = latitudeLongitudes.get(i).getMarker();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                SearchActivity.this, android.R.layout.simple_list_item_1, data
        );
        etCity.setAdapter(adapter);
        etCity.setThreshold(1);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Load Kathmandu  city when application launches
        mMap = googleMap;
        center = CameraUpdateFactory.newLatLng(new LatLng(27.7172453,85.3239605));
        zoom = CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
