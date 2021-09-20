package pro.kidss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps_Activity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    String lat,lon;
    int lt,ln;
    float f1,f2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maps_ );
        Intent intent = getIntent();
        lat = intent.getStringExtra( "lat" );
        lon = intent.getStringExtra( "lon" );
         f1= Float.parseFloat(lat);
         f2= Float.parseFloat(lon);
//        Log.e( "BBBb", String.valueOf( Integer.parseInt(lat) ) );
//        Log.e( "cdcasc", String.valueOf( Integer.parseInt(lon) ));


        SupportMapFragment mapFragment =(SupportMapFragment)getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map=googleMap;
        LatLng Locat = new LatLng(  f1, f2 );
        map.addMarker( new MarkerOptions().position( Locat ).title( "Phone" ) );
        map.moveCamera( CameraUpdateFactory.newLatLng( Locat ) );

    }
}