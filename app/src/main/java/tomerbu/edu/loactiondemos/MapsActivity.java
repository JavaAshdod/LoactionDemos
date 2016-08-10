package tomerbu.edu.loactiondemos;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks {


    private static final int REQUEST_LOCATION = 10;
    private GoogleApiClient client;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setupMap(savedInstanceState);
        initApiClient();
    }

    private void initApiClient() {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this);
        client = builder.build();
        client.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocation();
    }

    //
    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }
        //May be null:
        Location location = LocationServices.FusedLocationApi.getLastLocation(client);
        if (location == null) {
            initALocationRequest();
        } else
            Toast.makeText(MapsActivity.this, "location" + location.toString(), Toast.LENGTH_SHORT).show();
    }

    private void initALocationRequest() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationRequest request = LocationRequest.create().
                setInterval(1000).
                setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(MapsActivity.this, location.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_LOCATION == requestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        client.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng ashdod = new LatLng(31.8153095, 34.6564748);
        mMap = map;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        MarkerOptions marker = new MarkerOptions().
                position(ashdod).
                title("Ashdod").
                icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)).
                snippet("Minhal college");


        map.addMarker(marker);


        map.animateCamera(CameraUpdateFactory.newLatLngZoom(ashdod, 17));
    }


    private void setupMap(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            //init the fragment
            SupportMapFragment mapFragment = new SupportMapFragment();
            //get the map:
            mapFragment.getMapAsync(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mapFragment, "Map").commit();
        }

    }

}
