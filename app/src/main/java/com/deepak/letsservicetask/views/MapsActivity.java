package com.deepak.letsservicetask.views;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ListView;
import android.widget.Toast;

import com.deepak.letsservicetask.R;
import com.deepak.letsservicetask.database.LocationDB;
import com.deepak.letsservicetask.models.Constants;
import com.deepak.letsservicetask.models.LocationAdapter;
import com.deepak.letsservicetask.models.LocationDetails;
import com.deepak.letsservicetask.service.LocationBroadcastService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;
    public static Marker currentLocationMarker;
    private static final int COARSE_LOCATION_PERMISSION_CODE = 10;
    private static final int LOCATION_PERMISSION_CODE = 11;
    private Intent locationIntent;
    public LocationDB locationDB;

    @BindView(R.id.locationList)
    ListView locationList;

    /*@BindView(R.id.textView)
    TextView textView;*/

    LocationAdapter locationAdapter;
    ArrayList<LocationDetails> locationDetailsArrayList;
    LocationDetails locationDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        requestLocationPermission();
        requestCoarseLocationPermission();

        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationDB = new LocationDB(this);
        locationDB.open();

        locationDetailsArrayList = new ArrayList<>();
        locationAdapter = new LocationAdapter(this, locationDetailsArrayList);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        double latitude = intent.getDoubleExtra(LocationBroadcastService.EXTRA_LATITUDE, 0);
                        double longitude = intent.getDoubleExtra(LocationBroadcastService.EXTRA_LONGITUDE, 0);
                        double speed = intent.getDoubleExtra(LocationBroadcastService.EXTRA_SPEED, 0);
                        //textView.setText("Lat: " + latitude + ", Lng: " + longitude);
                        setCurrentMarker(latitude, longitude, speed);
                        locationDB.insertLocation(""+latitude, ""+longitude, ""+speed, Constants.GetToday());
                        UpdateListView();
                    }
                }, new IntentFilter(LocationBroadcastService.ACTION_LOCATION_BROADCAST)
        );
        locationList.setAdapter(locationAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, LocationBroadcastService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(this, LocationBroadcastService.class));
    }

    public static void setCurrentMarker(double lat, double lng, double speed) {

        if (mMap != null) {
            LatLng currentLocation = new LatLng(lat, lng);

            if (currentLocationMarker != null)
            {
                currentLocationMarker.remove();
            }
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation).title("Current Location");
            currentLocationMarker = mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(currentLocation)
                    .zoom(12)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    public void UpdateListView(){
        Cursor c = locationDB.queryLocationDetails();
        if(c.moveToLast()) {
            //int a = c.getCount();
            //for(int i = 0; i <= a; i++){
            locationDetails = new LocationDetails(c.getString(1), c.getString(2), c.getString(3), c.getString(4));
            locationList.setAdapter(locationAdapter);
            //}
        }
        locationDetailsArrayList.add(locationDetails);
        locationAdapter.notifyDataSetChanged();
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
        setCurrentMarker(13.9716, 77.5946, 0.0);
    }

    private void requestCoarseLocationPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION_PERMISSION_CODE);
        }
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE: {
                //If permission is granted
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case COARSE_LOCATION_PERMISSION_CODE: {
                //If permission is granted
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        locationDB.close();
        super.onDestroy();
    }
}
