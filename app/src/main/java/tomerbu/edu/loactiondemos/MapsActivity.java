package tomerbu.edu.loactiondemos;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (savedInstanceState == null){
            //init the fragment
            SupportMapFragment mapFragment = new SupportMapFragment();
            //get the map:
            mapFragment.getMapAsync(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mapFragment, "Map").commit();



        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng ashdod = new LatLng(31.8153095,34.6564748);

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        MarkerOptions marker = new MarkerOptions().
                position(ashdod).
                title("Ashdod").
                icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)).
                snippet("Minhal college");


        map.addMarker(marker);


        map.animateCamera(CameraUpdateFactory.newLatLngZoom(ashdod, 17));
    }
}
